package br.com.fiap.saude.model;

import java.util.Date;

public class Paciente {
    private Long idPaciente;
    private Long idUsuario;
    private Date dtNascimento;
    private String cpf;
    private String prioridadeAcessibilidade;

    public Paciente() { }

    public Paciente(Long idPaciente, Long idUsuario, Date dtNascimento, String cpf, String prioridadeAcessibilidade) {
        this.idPaciente = idPaciente;
        this.idUsuario = idUsuario;
        this.dtNascimento = dtNascimento;
        this.cpf = cpf;
        this.prioridadeAcessibilidade = prioridadeAcessibilidade;
    }

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getPrioridadeAcessibilidade() { return prioridadeAcessibilidade; }
    public void setPrioridadeAcessibilidade(String prioridadeAcessibilidade) { this.prioridadeAcessibilidade = prioridadeAcessibilidade; }
}
