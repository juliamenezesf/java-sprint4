package br.com.fiap.saude.http;

import br.com.fiap.saude.model.SuporteSolicitacao;
import br.com.fiap.saude.service.SuporteSolicitacaoService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SuporteSolicitacaoController {

    private final SuporteSolicitacaoService service;

    public SuporteSolicitacaoController(SuporteSolicitacaoService service) {
        this.service = service;
    }

    public void listar(HttpExchange exchange) throws IOException {
        try {
            List<SuporteSolicitacao> todos = service.listar();
            JsonUtil.send(exchange, 200, todos);
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        }
    }

    public void listarAbertas(HttpExchange exchange) throws IOException {
        try {
            List<SuporteSolicitacao> abertos = service.listarAbertas();
            JsonUtil.send(exchange, 200, abertos);
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        }
    }

    public void criar(HttpExchange exchange) throws IOException {
        try {
            SuporteSolicitacao dto = JsonUtil.fromJson(exchange.getRequestBody(), SuporteSolicitacao.class);

            if (dto == null
                    || dto.getNomeContato() == null || dto.getNomeContato().isBlank()
                    || dto.getProblemaRelato() == null || dto.getProblemaRelato().isBlank()) {
                JsonUtil.send(exchange, 400, Map.of(
                        "error", "VALIDATION",
                        "details", "Campos obrigatórios: nomeContato, problemaRelato"
                ));
                return;
            }

            long id = service.criar(dto);
            Optional<SuporteSolicitacao> criado = service.buscarPorId(id);

            JsonUtil.send(exchange, 201, criado.orElseGet(() -> {
                dto.setIdSolicitacao(id);
                return dto;
            }));

        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        } catch (Exception e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SERVER", "details", e.getMessage()));
        }
    }
}

