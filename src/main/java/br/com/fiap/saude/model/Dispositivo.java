package br.com.fiap.saude.model;

public class Dispositivo {
    private Long idDispositivo;
    private String tipo;
    private String sistemaOperacional;
    private String usaCamera;

    public Dispositivo() { }

    public Dispositivo(Long idDispositivo, String tipo, String sistemaOperacional, String usaCamera) {
        this.idDispositivo = idDispositivo;
        this.tipo = tipo;
        this.sistemaOperacional = sistemaOperacional;
        this.usaCamera = usaCamera;
    }

    public Long getIdDispositivo() { return idDispositivo; }
    public void setIdDispositivo(Long idDispositivo) { this.idDispositivo = idDispositivo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getSistemaOperacional() { return sistemaOperacional; }
    public void setSistemaOperacional(String sistemaOperacional) { this.sistemaOperacional = sistemaOperacional; }
    public String getUsaCamera() { return usaCamera; }
    public void setUsaCamera(String usaCamera) { this.usaCamera = usaCamera; }
}
