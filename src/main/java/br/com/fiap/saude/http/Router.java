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

        server.createContext("/api/solicitacoes", exchange -> {
            try {
                String method = exchange.getRequestMethod().toUpperCase();
                if ("GET".equals(method)) {
                    controller.listar(exchange);
                } else if ("POST".equals(method)) {
                    controller.criar(exchange);
                } else {
                    JsonUtil.send(exchange, 405, Map.of("error", "Método não permitido"));
                }
            } finally {
                exchange.close();
            }
        });

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

        System.out.println("✅ HTTP server ON http://localhost:8080");
        server.start();
    }
}

