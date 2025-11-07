package br.com.fiap.saude.http;

import br.com.fiap.saude.service.SuporteSolicitacaoService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Router {

    public static void startServer() throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        // bind em 0.0.0.0 para aceitar tráfego externo (Render/containers)
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        var controller = new SuporteSolicitacaoController(new SuporteSolicitacaoService());

        String basePath = "/api/solicitacoes";

        // ---------- ROOT e HEALTHZ ----------
        server.createContext("/", exchange -> {
            try {
                addCorsHeaders(exchange.getResponseHeaders());

                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                String p = exchange.getRequestURI().getPath();
                if ("/".equals(p)) {
                    String response = "Saude Digital - API formulario - Backend ativo! Acesse /api/solicitacoes";
                    byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, bytes.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(bytes);
                    }
                    return;
                }

                if ("/healthz".equals(p)) {
                    byte[] ok = "ok".getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
                    exchange.sendResponseHeaders(200, ok.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(ok);
                    }
                    return;
                }

                JsonUtil.send(exchange, 404, Map.of("error", "NOT_FOUND", "details", "Recurso não encontrado"));
            } finally {
                exchange.close();
            }
        });

        // ---------- /api/solicitacoes e /api/solicitacoes/{id} ----------
        server.createContext(basePath, exchange -> {
            try {
                addCorsHeaders(exchange.getResponseHeaders());

                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                String method = exchange.getRequestMethod().toUpperCase();
                String path = normalizePath(exchange.getRequestURI().getPath()); // ex: /api/solicitacoes/10

                if (path.equals(basePath)) {
                    if ("GET".equals(method)) {
                        controller.listar(exchange);
                    } else if ("POST".equals(method)) {
                        controller.criar(exchange);
                    } else {
                        JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                    }
                    return;
                }

                if (path.startsWith(basePath + "/")) {
                    String idStr = path.substring((basePath + "/").length());
                    try {
                        long id = Long.parseLong(idStr);
                        if ("GET".equals(method)) {
                            controller.buscarPorId(exchange, id);
                        } else if ("PUT".equals(method)) {
                            controller.atualizarStatus(exchange, id);
                        } else if ("DELETE".equals(method)) {
                            controller.deletar(exchange, id);
                        } else {
                            JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                        }
                    } catch (NumberFormatException e) {
                        JsonUtil.send(exchange, 400, Map.of(
                                "error", "BAD_REQUEST",
                                "details", "ID inválido na URL"
                        ));
                    }
                    return;
                }

                JsonUtil.send(exchange, 404, Map.of("error", "NOT_FOUND", "details", "Recurso não encontrado"));
            } finally {
                exchange.close();
            }
        });

        // ---------- /api/solicitacoes/abertas ----------
        server.createContext("/api/solicitacoes/abertas", exchange -> {
            try {
                addCorsHeaders(exchange.getResponseHeaders());

                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    controller.listarAbertas(exchange);
                } else {
                    JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                }
            } finally {
                exchange.close();
            }
        });

        System.out.println("HTTP server ON http://0.0.0.0:" + port);
        server.start();
    }

    // --------- helpers ---------
    private static void addCorsHeaders(Headers h) {
        h.add("Access-Control-Allow-Origin", "*");
        h.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        h.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        h.add("Content-Type", "application/json; charset=utf-8");
    }

    private static String normalizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        if (path.length() > 1 && path.endsWith("/")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }
}
