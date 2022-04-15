package model;

import java.util.ArrayList;

public class ModelContexto {

	private String urlRedirecionamento;
	
	private ModelUsuario usuario;
	private ArrayList<ModelUsuario> listaUsuarios = new ArrayList<ModelUsuario>();
	private ArrayList<ModelFeedback> listaFeedbacks = new ArrayList<ModelFeedback>();
	private ArrayList<ModelMorador> listaMoradores = new ArrayList<ModelMorador>();
	
	public String getUrlRedirecionamento() {
		return urlRedirecionamento;
	}
	
	public void setUrlRedirecionamento(String urlRedirecionamento) {
		this.urlRedirecionamento = urlRedirecionamento;
	}
	
	public ModelUsuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}
	
	public ArrayList<ModelUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	
	public void setListaUsuarios(ArrayList<ModelUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	public ArrayList<ModelFeedback> getListaFeedbacks() {
		return listaFeedbacks;
	}
	
	public void setListaFeedbacks(ArrayList<ModelFeedback> listaFeedbacks) {
		this.listaFeedbacks = listaFeedbacks;
	}

	public ArrayList<ModelMorador> getListaMoradores() {
		return listaMoradores;
	}

	public void setListaMoradores(ArrayList<ModelMorador> listaMoradores) {
		this.listaMoradores = listaMoradores;
	}
}