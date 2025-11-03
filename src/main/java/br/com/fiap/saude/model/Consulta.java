package br.com.fiap.saude.model;

import java.util.Date;

public class Consulta {
    private Long idConsulta;
    private Long idPaciente;
    private Long idMedico;
    private Date dtConsulta;
    private String status;
    private String canal;
    private String descricao;

    public Consulta() { }

    public Consulta(Long idConsulta, Long idPaciente, Long idMedico, Date dtConsulta, String status, String canal, String descricao) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dtConsulta = dtConsulta;
        this.status = status;
        this.canal = canal;
        this.descricao = descricao;
    }

    public Long getIdConsulta() { return idConsulta; }
    public void setIdConsulta(Long idConsulta) { this.idConsulta = idConsulta; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }
    public Date getDtConsulta() { return dtConsulta; }
    public void setDtConsulta(Date dtConsulta) { this.dtConsulta = dtConsulta; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
