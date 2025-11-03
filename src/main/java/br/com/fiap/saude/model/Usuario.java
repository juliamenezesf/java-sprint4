package br.com.fiap.saude.model;

import java.util.Date;

public class Usuario {
    private Long idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private String perfil;
    private Date dtCadastro;

    public Usuario() { }

    public Usuario(Long idUsuario, String nome, String email, String telefone, String perfil, Date dtCadastro) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.perfil = perfil;
        this.dtCadastro = dtCadastro;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public Date getDtCadastro() { return dtCadastro; }
    public void setDtCadastro(Date dtCadastro) { this.dtCadastro = dtCadastro; }
}
