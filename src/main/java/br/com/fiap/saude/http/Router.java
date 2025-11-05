package br.com.fiap.saude.http;

import br.com.fiap.saude.service.SuporteSolicitacaoService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class Router {

    // Ajuste aqui se quiser liberar outro origin (ex.: deploy futuro)
    private static final String FRONTEND_ORIGIN = "http://localhost:5173";

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        var controller = new SuporteSolicitacaoController(new SuporteSolicitacaoService());

        // /api/solicitacoes  -> GET / POST / OPTIONS (preflight)
        server.createContext("/api/solicitacoes", exchange -> {
            try {
                addCorsHeaders(exchange);

                String method = exchange.getRequestMethod().toUpperCase();

                // pré-flight do browser
                if ("OPTIONS".equals(method)) {
                    handlePreflight(exchange);
                    return;
                }

                switch (method) {
                    case "GET" -> controller.listar(exchange);
                    case "POST" -> controller.criar(exchange);
                    default ->
                            JsonUtil.send(exchange, 405,
                                    Map.of("error", "Método não permitido"));
                }
            } finally {
                exchange.close();
            }
        });

        // /api/solicitacoes/abertas -> GET / OPTIONS
        server.createContext("/api/solicitacoes/abertas", exchange -> {
            try {
                addCorsHeaders(exchange);

                String method = exchange.getRequestMethod().toUpperCase();

                if ("OPTIONS".equals(method)) {
                    handlePreflight(exchange);
                    return;
                }

                if ("GET".equals(method)) {
                    controller.listarAbertas(exchange);
                } else {
                    JsonUtil.send(exchange, 405,
                            Map.of("error", "Método não permitido"));
                }
            } finally {
                exchange.close();
            }
        });

        System.out.println("✅ HTTP server ON http://localhost:8080");
        server.start();
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.set("Access-Control-Allow-Origin", FRONTEND_ORIGIN);
        headers.set("Vary", "Origin");
        headers.set("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Content-Type");
        // se quiser permitir cookies no futuro:
        // headers.set("Access-Control-Allow-Credentials", "true");
    }

    private static void handlePreflight(HttpExchange exchange) throws IOException {
        // 204 No Content para o pré-flight
        exchange.sendResponseHeaders(204, -1);
    }
}

