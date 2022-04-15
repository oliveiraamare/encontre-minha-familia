package model;

import java.util.ArrayList;

import org.json.JSONObject;

import dao.DAOAdminRepository;
import dao.DAOFeedbackRepository;

public class ModelAdministrador {
	
	private String contato;
	private ArrayList<ModelPermissao> permissoes = new ArrayList<>();
	
	private static DAOAdminRepository dAOAdminRepository = new DAOAdminRepository();
	private static DAOFeedbackRepository daoFeedbackRepository = new DAOFeedbackRepository();
	
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public ArrayList<ModelPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(ArrayList<ModelPermissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	public void cadastrarAdmin(ModelUsuario usuario, ModelAdministrador administrador) throws Exception {
		dAOAdminRepository.cadastrarUsuario(usuario, administrador);
	}
	
	public ModelUsuario atualizarAdministrador(ModelUsuario user, ModelAdministrador admin) throws Exception {
		return dAOAdminRepository.atualizarUsuario(user, admin);
	}
	
	public ArrayList<ModelUsuario> consultarAcessos() throws Exception{
		return dAOAdminRepository.consultarUsuarioAcesso();
	}
	
	public void habilitarUsuario(Long id) throws Exception {
		dAOAdminRepository.habilitarUsuario(id);
	}
	
	public void desabilitarUsuario(Long id) throws Exception {
		dAOAdminRepository.desabilitarUsuario(id);
	}
	
	public ArrayList<ModelFeedback> buscarFeedback() throws Exception {
		return daoFeedbackRepository.consultarFeedback();
	}
	
	/*public ArrayList<String> buscarFeedbackChart() throws Exception {
		
			
		ArrayList<String> feedbacks = new ArrayList<>(); 
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		String experiencia = gson.toJson(daoFeedbackRepository.consultarTotalExperiencia());
		System.out.println(experiencia);
		String facilidade = gson.toJson(daoFeedbackRepository.consultarTotalFacilidade());
		String importancia = gson.toJson(daoFeedbackRepository.consultarImportancia());
		String sugestao = gson.toJson(daoFeedbackRepository.consultarSugestao());
		
		feedbacks.add(experiencia);
		feedbacks.add(facilidade);
		feedbacks.add(importancia);
		feedbacks.add(sugestao);
		
		//feedbacks.addAll(daoFeedbackRepository.consultarTotalExperiencia(), daoFeedbackRepository.consultarTotalExperiencia(), 
					//	daoFeedbackRepository.consultarTotalExperiencia(), daoFeedbackRepository.consultarTotalExperiencia());
				
				
		return feedbacks;
	}	*/
	
	public JSONObject buscarFeedbackChart() throws Exception {
		
		//JSONArray jsonArray = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("experiencia", daoFeedbackRepository.consultarTotalExperiencia());
		obj.put("facilidade", daoFeedbackRepository.consultarTotalFacilidade());
		obj.put("importancia", daoFeedbackRepository.consultarImportancia());
		obj.put("sugestao", daoFeedbackRepository.consultarSugestao());
		//jsonArray.put(obj);
		
		//return jsonArray;
		
		String json_string = daoFeedbackRepository.consultarTotalExperiencia().toString();
		System.out.println("objeto original -> " + json_string);
		System.out.println();
		
		return obj;
		//return daoFeedbackRepository.consultarTotalExperiencia();
		
	}	
}