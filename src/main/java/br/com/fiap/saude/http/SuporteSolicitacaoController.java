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

    // GET /api/solicitacoes
    public void listar(HttpExchange exchange) throws IOException {
        try {
            List<SuporteSolicitacao> todos = service.listar();
            JsonUtil.send(exchange, 200, todos);
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        }
    }

    // GET /api/solicitacoes/abertas
    public void listarAbertas(HttpExchange exchange) throws IOException {
        try {
            List<SuporteSolicitacao> abertos = service.listarAbertas();
            JsonUtil.send(exchange, 200, abertos);
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        }
    }

    // GET /api/solicitacoes/{id}
    public void buscarPorId(HttpExchange exchange, long id) throws IOException {
        try {
            Optional<SuporteSolicitacao> opt = service.buscarPorId(id);
            if (opt.isPresent()) {
                JsonUtil.send(exchange, 200, opt.get());
            } else {
                JsonUtil.send(exchange, 404, Map.of(
                        "error", "NOT_FOUND",
                        "details", "Solicitação " + id + " não encontrada"
                ));
            }
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        }
    }

    // POST /api/solicitacoes
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

    // PUT /api/solicitacoes/{id} — atualizar status
    public void atualizarStatus(HttpExchange exchange, long id) throws IOException {
        try {
            Map body = JsonUtil.fromJson(exchange.getRequestBody(), Map.class);

            String novoStatus = null;
            if (body != null && body.get("statusAtendimento") != null) {
                novoStatus = body.get("statusAtendimento").toString();
            }

            if (novoStatus == null || novoStatus.isBlank()) {
                JsonUtil.send(exchange, 400, Map.of(
                        "error", "VALIDATION",
                        "details", "Campo obrigatório: statusAtendimento"
                ));
                return;
            }

            String up = novoStatus.toUpperCase();
            if (!up.equals("ABERTO")
                    && !up.equals("EM ANDAMENTO")
                    && !up.equals("RESOLVIDO")) {
                JsonUtil.send(exchange, 400, Map.of(
                        "error", "VALIDATION",
                        "details", "statusAtendimento deve ser ABERTO, EM ANDAMENTO ou RESOLVIDO"
                ));
                return;
            }

            boolean ok = service.atualizarStatus(id, up);
            if (!ok) {
                JsonUtil.send(exchange, 404, Map.of(
                        "error", "NOT_FOUND",
                        "details", "Solicitação " + id + " não encontrada"
                ));
                return;
            }

            Optional<SuporteSolicitacao> atualizado = service.buscarPorId(id);
            if (atualizado.isPresent()) {
                JsonUtil.send(exchange, 200, atualizado.get());
            } else {
                JsonUtil.send(exchange, 200, Map.of(
                        "message", "Status atualizado com sucesso"
                ));
            }

        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        } catch (Exception e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SERVER", "details", e.getMessage()));
        }
    }

    // DELETE /api/solicitacoes/{id}
    public void deletar(HttpExchange exchange, long id) throws IOException {
        try {
            boolean ok = service.deletar(id);
            if (!ok) {
                JsonUtil.send(exchange, 404, Map.of(
                        "error", "NOT_FOUND",
                        "details", "Solicitação " + id + " não encontrada"
                ));
            } else {
                JsonUtil.send(exchange, 204, null); // 204 No Content
            }
        } catch (SQLException e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SQL", "details", e.getMessage()));
        } catch (Exception e) {
            JsonUtil.send(exchange, 500, Map.of("error", "SERVER", "details", e.getMessage()));
        }
    }
}


