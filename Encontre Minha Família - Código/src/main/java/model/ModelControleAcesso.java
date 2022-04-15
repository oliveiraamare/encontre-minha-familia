package model;

import java.sql.Timestamp;

public class ModelControleAcesso {
	
	private Long idAcesso;
	private Timestamp ultimoAcesso;
	private Timestamp dataCriacao;
	
	public Long getIdAcesso() {
		return idAcesso;
	}
	
	public void setIdAcesso(Long idAcesso) {
		this.idAcesso = idAcesso;
	}
	
	public Timestamp getUltimoAcesso() {
		return ultimoAcesso;
	}
	
	public void setUltimoAcesso(Timestamp ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	
	public Timestamp getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}