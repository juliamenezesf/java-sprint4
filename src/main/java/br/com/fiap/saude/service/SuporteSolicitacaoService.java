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

    public long criar(SuporteSolicitacao s) throws SQLException {
        // regra de negócio mínima
        if (s == null) throw new SQLException("Objeto nulo");
        if (s.getNomeContato() == null || s.getNomeContato().isBlank())
            throw new SQLException("NOME_CONTATO obrigatório");
        if (s.getProblemaRelato() == null || s.getProblemaRelato().isBlank())
            throw new SQLException("PROBLEMA_RELATO obrigatório");

        return dao.insert(s);
    }

    public Optional<SuporteSolicitacao> buscarPorId(long id) throws SQLException {
        return dao.findById(id);
    }

    public List<SuporteSolicitacao> listar() throws SQLException {
        return dao.findAll();
    }

    public List<SuporteSolicitacao> listarAbertas() throws SQLException {
        return dao.findAbertasOuEmAndamento();
    }
}

