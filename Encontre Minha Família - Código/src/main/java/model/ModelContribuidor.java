package model;

import java.util.ArrayList;

import dao.DAOFeedbackRepository;
import dao.DAOMoradorRepository;
import dao.DAOUsuarioRepository;

public class ModelContribuidor {
	
	private String cidade;
	private String estado;
	private ArrayList<ModelPermissao> permissoes = new ArrayList<>();
	
	private static DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	private static DAOFeedbackRepository daoFeedbackRepository = new DAOFeedbackRepository();
	private static DAOMoradorRepository daoMoradorRepository = new DAOMoradorRepository();
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArrayList<ModelPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(ArrayList<ModelPermissao> permissoes) {
		this.permissoes = permissoes;
	}	
	
	public ModelUsuario atualizarContribuidor(ModelUsuario usuario, ModelContribuidor contribuidor) throws Exception {
		return daoUsuarioRepository.atualizarUsuario(usuario, contribuidor);
	}
	
	public void gravarFeedback(ModelFeedback feedback) throws Exception {
		daoFeedbackRepository.gravarFeedback(feedback);
	}
	
	public void gravarMorador(ModelMorador morador, ModelImagem imagem, ModelUltimaLocalidade ultimaLocalidade) throws Exception {
		daoMoradorRepository.gravarMorador(morador, imagem, ultimaLocalidade);
	}
	
	public ArrayList<ModelMorador> buscarMorador(Long id) throws Exception {
		return daoMoradorRepository.buscarMorador(id);
	}
	
	public ArrayList<ModelMorador> buscarMoradorAll() throws Exception {
		return daoMoradorRepository.buscarMoradorAll();
	}
	
	public void deletarMorador(Long id) throws Exception {
		daoMoradorRepository.deletarMorador(id);
	}
	
	public void atualizarMorador(ModelMorador morador, ModelUltimaLocalidade ultimaLocalidade) throws Exception {
		daoMoradorRepository.atualizarMorador(morador, ultimaLocalidade);
	}
}