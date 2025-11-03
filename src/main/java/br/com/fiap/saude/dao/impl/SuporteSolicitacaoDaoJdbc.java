package br.com.fiap.saude.dao.impl;

import br.com.fiap.saude.config.DatabaseConfig;
import br.com.fiap.saude.dao.SuporteSolicitacaoDAO;
import br.com.fiap.saude.model.SuporteSolicitacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuporteSolicitacaoDaoJdbc implements SuporteSolicitacaoDAO {

    @Override
    public long insert(SuporteSolicitacao s) throws SQLException {
        final String sql =
                "INSERT INTO suporte_solicitacao " +
                        "(nome_contato, telefone_contato, problema_relato, status_atendimento, dt_criacao) " +
                        "VALUES (?,?,?,?, SYSDATE)";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_solicitacao"})) {

            if (s.getNomeContato() == null || s.getNomeContato().isBlank())
                throw new SQLException("NOME_CONTATO obrigatório");

            if (s.getProblemaRelato() == null || s.getProblemaRelato().isBlank())
                throw new SQLException("PROBLEMA_RELATO obrigatório");

            ps.setString(1, s.getNomeContato());

            if (s.getTelefoneContato() == null) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, s.getTelefoneContato());
            }

            ps.setString(3, s.getProblemaRelato());

            String status = (s.getStatusAtendimento() == null || s.getStatusAtendimento().isBlank())
                    ? "ABERTO" : s.getStatusAtendimento();
            ps.setString(4, status);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1L;
    }

    @Override
    public Optional<SuporteSolicitacao> findById(long id) throws SQLException {
        String sql = "SELECT id_solicitacao, nome_contato, telefone_contato, problema_relato," +
                "       status_atendimento, dt_criacao " +
                "  FROM suporte_solicitacao WHERE id_solicitacao = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<SuporteSolicitacao> findAll() throws SQLException {
        String sql = "SELECT id_solicitacao, nome_contato, telefone_contato, problema_relato," +
                "       status_atendimento, dt_criacao " +
                "  FROM suporte_solicitacao ORDER BY dt_criacao DESC";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<SuporteSolicitacao> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    @Override
    public List<SuporteSolicitacao> findAbertasOuEmAndamento() throws SQLException {
        String sql = "SELECT id_solicitacao, nome_contato, telefone_contato, problema_relato," +
                "       status_atendimento, dt_criacao " +
                "  FROM suporte_solicitacao " +
                " WHERE status_atendimento IN ('ABERTO','EM ANDAMENTO') " +
                " ORDER BY dt_criacao DESC";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<SuporteSolicitacao> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    @Override
    public int updateStatus(long id, String novoStatus) throws SQLException {
        String sql = "UPDATE suporte_solicitacao SET status_atendimento = ? WHERE id_solicitacao = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setLong(2, id);
            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(long id) throws SQLException {
        String sql = "DELETE FROM suporte_solicitacao WHERE id_solicitacao = ?";
        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }

    private SuporteSolicitacao map(ResultSet rs) throws SQLException {
        SuporteSolicitacao s = new SuporteSolicitacao();
        s.setIdSolicitacao(rs.getLong("id_solicitacao"));
        s.setNomeContato(rs.getString("nome_contato"));
        s.setTelefoneContato(rs.getString("telefone_contato"));
        s.setProblemaRelato(rs.getString("problema_relato"));
        s.setStatusAtendimento(rs.getString("status_atendimento"));

        Timestamp ts = rs.getTimestamp("dt_criacao");
        if (ts != null) {
            s.setDtCriacao(new java.util.Date(ts.getTime())); // usa java.util.Date na model
        }
        return s;
    }
}
