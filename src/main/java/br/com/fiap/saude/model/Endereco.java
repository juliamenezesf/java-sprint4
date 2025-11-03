package br.com.fiap.saude.model;

public class Endereco {
    private Long idEndereco;
    private Long idUsuario;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco() { }

    public Endereco(Long idEndereco, Long idUsuario, String cep, String logradouro, String numero, String bairro, String cidade, String uf) {
        this.idEndereco = idEndereco;
        this.idUsuario = idUsuario;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Long getIdEndereco() { return idEndereco; }
    public void setIdEndereco(Long idEndereco) { this.idEndereco = idEndereco; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
}
