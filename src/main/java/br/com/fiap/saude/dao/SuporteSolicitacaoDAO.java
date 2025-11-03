package br.com.fiap.saude.dao;

import br.com.fiap.saude.model.SuporteSolicitacao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SuporteSolicitacaoDAO {
    long insert(SuporteSolicitacao s) throws SQLException;
    Optional<SuporteSolicitacao> findById(long id) throws SQLException;
    List<SuporteSolicitacao> findAll() throws SQLException;
    List<SuporteSolicitacao> findAbertasOuEmAndamento() throws SQLException;
    int updateStatus(long id, String novoStatus) throws SQLException;
    int delete(long id) throws SQLException;
}
