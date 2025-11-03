package br.com.fiap.saude.model;

public class ConsultaChecklistPasso {
    private Long idConsulta;
    private Long idPasso;
    private String concluido;

    public ConsultaChecklistPasso() { }

    public ConsultaChecklistPasso(Long idConsulta, Long idPasso, String concluido) {
        this.idConsulta = idConsulta;
        this.idPasso = idPasso;
        this.concluido = concluido;
    }

    public Long getIdConsulta() { return idConsulta; }
    public void setIdConsulta(Long idConsulta) { this.idConsulta = idConsulta; }
    public Long getIdPasso() { return idPasso; }
    public void setIdPasso(Long idPasso) { this.idPasso = idPasso; }
    public String getConcluido() { return concluido; }
    public void setConcluido(String concluido) { this.concluido = concluido; }
}
