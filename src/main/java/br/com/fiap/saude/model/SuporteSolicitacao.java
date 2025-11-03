package br.com.fiap.saude.model;

import java.util.Date; // ← garante que está importando isso

public class SuporteSolicitacao {

    private Long idSolicitacao;
    private String nomeContato;
    private String telefoneContato;
    private String problemaRelato;
    private String statusAtendimento;
    private Date dtCriacao; // ← ALTERA pra java.util.Date

    public Long getIdSolicitacao() { return idSolicitacao; }
    public void setIdSolicitacao(Long idSolicitacao) { this.idSolicitacao = idSolicitacao; }

    public String getNomeContato() { return nomeContato; }
    public void setNomeContato(String nomeContato) { this.nomeContato = nomeContato; }

    public String getTelefoneContato() { return telefoneContato; }
    public void setTelefoneContato(String telefoneContato) { this.telefoneContato = telefoneContato; }

    public String getProblemaRelato() { return problemaRelato; }
    public void setProblemaRelato(String problemaRelato) { this.problemaRelato = problemaRelato; }

    public String getStatusAtendimento() { return statusAtendimento; }
    public void setStatusAtendimento(String statusAtendimento) { this.statusAtendimento = statusAtendimento; }

    public Date getDtCriacao() { return dtCriacao; }
    public void setDtCriacao(Date dtCriacao) { this.dtCriacao = dtCriacao; }
}
