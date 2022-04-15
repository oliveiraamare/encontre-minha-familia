package model;

import java.util.ArrayList;

public class ModelFeedback {
	
	private String experiencia;
	private String importancia;
	private String facilidade;
	private String sugestao;
	private Long usuarioID;
	private Integer total;
	
	private ArrayList<ModelFeedback> totalFeedback = new ArrayList<>();
	
	public String getExperiencia() {
		return experiencia;
	}
	
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}
	
	public String getImportancia() {
		return importancia;
	}
	
	public void setImportancia(String importancia) {
		this.importancia = importancia;
	}
		
	public String getFacilidade() {
		return facilidade;
	}
	
	public void setFacilidade(String facilidade) {
		this.facilidade = facilidade;
	}
	
	public String getSugestao() {
		return sugestao;
	}
	
	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}

	public Long getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(Long usuarioID) {
		this.usuarioID = usuarioID;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public ArrayList<ModelFeedback> getTotalFeedback() {
		return totalFeedback;
	}

	public void setTotalFeedback(ArrayList<ModelFeedback> totalFeedback) {
		this.totalFeedback = totalFeedback;
	}
}