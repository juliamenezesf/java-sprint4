package br.com.fiap.saude.model;

public class ChecklistPasso {
    private Long idPasso;
    private Integer ordem;
    private String descricao;

    public ChecklistPasso() { }

    public ChecklistPasso(Long idPasso, Integer ordem, String descricao) {
        this.idPasso = idPasso;
        this.ordem = ordem;
        this.descricao = descricao;
    }

    public Long getIdPasso() { return idPasso; }
    public void setIdPasso(Long idPasso) { this.idPasso = idPasso; }
    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
