package model;

import java.io.Serializable;

import enums.TipoUsuario;

public class ModelUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String senha;
	private String nome;
	private String email;
	private boolean ativo;
	
	private TipoUsuario tipoUsuario;	
	private ModelControleAcesso acesso;
	private ModelContribuidor contribuidor;
	private ModelFeedback feedback;
	private ModelAdministrador administrador;
		
	public ModelControleAcesso getAcesso() {
		return acesso;
	}

	public ModelContribuidor getContribuidor() {
		return contribuidor;
	}

	public void setContribuidor(ModelContribuidor contribuidor) {
		this.contribuidor = contribuidor;
	}

	public ModelFeedback getFeedback() {
		return feedback;
	}

	public void setFeedback(ModelFeedback feedback) {
		this.feedback = feedback;
	}
	
	public ModelAdministrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(ModelAdministrador administrador) {
		this.administrador = administrador;
	}

	public void setAcesso(ModelControleAcesso acesso) {
		this.acesso = acesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public boolean validar(ModelUsuario usuario) {

		String nome = usuario.getNome();
		String senha = usuario.getSenha();
		String user = usuario.getLogin();

		if ((nome == null || nome.isEmpty()) && (senha == null || senha.isEmpty())
				&& (user == null || user.isEmpty())) {
			return false;
		}

		return true;
	}
}