package br.com.fiap.saude.http;

import br.com.fiap.saude.service.SuporteSolicitacaoService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class Router {

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        var controller = new SuporteSolicitacaoController(new SuporteSolicitacaoService());

        String basePath = "/api/solicitacoes";

        // /api/solicitacoes  e  /api/solicitacoes/{id}
        server.createContext(basePath, exchange -> {
            try {
                String method = exchange.getRequestMethod().toUpperCase();
                String path = exchange.getRequestURI().getPath(); // ex: /api/solicitacoes/10

                if (path.equals(basePath)) {
                    // coleção
                    if ("GET".equals(method)) {
                        controller.listar(exchange);
                    } else if ("POST".equals(method)) {
                        controller.criar(exchange);
                    } else {
                        JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                    }
                } else if (path.startsWith(basePath + "/")) {
                    // detalhe: /api/solicitacoes/{id}
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
                } else {
                    JsonUtil.send(exchange, 404, Map.of("error", "NOT_FOUND", "details", "Recurso não encontrado"));
                }

            } finally {
                exchange.close();
            }
        });

        // /api/solicitacoes/abertas
        server.createContext("/api/solicitacoes/abertas", exchange -> {
            try {
                if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    controller.listarAbertas(exchange);
                } else {
                    JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                }
            } finally {
                exchange.close();
            }
        });
        // mensagem no navegador
        server.createContext("/", exchange -> {
            String response = "Saude Digital - API formulario - Backend ativo! Acesse /api/solicitacoes";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        System.out.println("HTTP server ON http://localhost:8080");
        server.start();
    }
}
