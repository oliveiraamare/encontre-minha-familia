package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ModelMorador implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long usuarioId;
	private String nome;
	private String genero;
	private String localVisto;
	private String aparencia;
	private String idade;
	private String cidade;
	private String contato;
	private Timestamp dataCadastro;
	private String situacao;
	private String estado;
	
	private ModelImagem imagem;
	private ModelUltimaLocalidade ultimalocalidade;
			
	public ModelImagem getImagem() {
		return imagem;
	}

	public void setImagem(ModelImagem imagem) {
		this.imagem = imagem;
	}

	public ModelUltimaLocalidade getUltimalocalidade() {
		return ultimalocalidade;
	}

	public void setUltimalocalidade(ModelUltimaLocalidade ultimalocalidade) {
		this.ultimalocalidade = ultimalocalidade;
	}

	public long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getLocalVisto() {
		return localVisto;
	}

	public void setLocalVisto(String localVisto) {
		this.localVisto = localVisto;
	}

	public String getAparencia() {
		return aparencia;
	}

	public void setAparencia(String aparencia) {
		this.aparencia = aparencia;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean validar(ModelMorador morador) {

		String local = morador.getLocalVisto();
		String genero = morador.getGenero();
		String aparencia = morador.getAparencia();

		if ((local == null || local.isEmpty()) && (genero == null || genero.isEmpty()) 
				&& (aparencia == null || aparencia.isEmpty()) ) {
			return false;
		}

		return true;
	}
}