package br.com.fiap.saude.service;

import br.com.fiap.saude.dao.SuporteSolicitacaoDAO;
import br.com.fiap.saude.dao.impl.SuporteSolicitacaoDaoJdbc;
import br.com.fiap.saude.model.SuporteSolicitacao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SuporteSolicitacaoService {

    private final SuporteSolicitacaoDAO dao;

    public SuporteSolicitacaoService() {
        this.dao = new SuporteSolicitacaoDaoJdbc();
    }

    public SuporteSolicitacaoService(SuporteSolicitacaoDAO dao) {
        this.dao = dao;
    }

    // GET /api/solicitacoes
    public List<SuporteSolicitacao> listar() throws SQLException {
        return dao.findAll();
    }

    // GET /api/solicitacoes/abertas
    public List<SuporteSolicitacao> listarAbertas() throws SQLException {
        return dao.findAbertasOuEmAndamento();
    }

    public Optional<SuporteSolicitacao> buscarPorId(long id) throws SQLException {
        return dao.findById(id);
    }

    // POST /api/solicitacoes
    public long criar(SuporteSolicitacao s) throws SQLException {
        return dao.insert(s);
    }

    // PUT /api/solicitacoes/{id}  (atualizar status)
    public boolean atualizarStatus(long id, String novoStatus) throws SQLException {
        int linhas = dao.updateStatus(id, novoStatus);
        return linhas > 0;
    }

    // DELETE /api/solicitacoes/{id}
    public boolean deletar(long id) throws SQLException {
        int linhas = dao.delete(id);
        return linhas > 0;
    }
}

