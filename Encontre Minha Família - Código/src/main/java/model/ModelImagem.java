package model;

public class ModelImagem {
	
	private Long id;
	private String imagem;
	private String extensaoImagem;
	private Long moradorID;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getExtensaoImagem() {
		return extensaoImagem;
	}
	
	public void setExtensaoImagem(String extensaoImagem) {
		this.extensaoImagem = extensaoImagem;
	}
	
	public Long getMoradorID() {
		return moradorID;
	}
	
	public void setUMoradorID(Long moradorID) {
		this.moradorID = moradorID;
	}
}