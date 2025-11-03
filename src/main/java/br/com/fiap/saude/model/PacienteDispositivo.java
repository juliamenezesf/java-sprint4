package br.com.fiap.saude.model;

public class PacienteDispositivo {
    private Long idPaciente;
    private Long idDispositivo;
    private String apelido;

    public PacienteDispositivo() { }

    public PacienteDispositivo(Long idPaciente, Long idDispositivo, String apelido) {
        this.idPaciente = idPaciente;
        this.idDispositivo = idDispositivo;
        this.apelido = apelido;
    }

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public Long getIdDispositivo() { return idDispositivo; }
    public void setIdDispositivo(Long idDispositivo) { this.idDispositivo = idDispositivo; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
}
