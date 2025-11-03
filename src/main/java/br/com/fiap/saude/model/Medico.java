package br.com.fiap.saude.model;

public class Medico {
    private Long idMedico;
    private Long idUsuario;
    private String crm;
    private String especialidade;

    public Medico() { }

    public Medico(Long idMedico, Long idUsuario, String crm, String especialidade) {
        this.idMedico = idMedico;
        this.idUsuario = idUsuario;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
