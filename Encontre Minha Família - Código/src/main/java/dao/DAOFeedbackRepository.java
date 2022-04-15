package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import connection.SingleConnection;
import model.ModelFeedback;

public class DAOFeedbackRepository {

	private Connection connection;

	public DAOFeedbackRepository() {
		connection = SingleConnection.getConnection();
	}

	public ArrayList<ModelFeedback> consultarFeedback() throws Exception {

		ArrayList<ModelFeedback> feedbacks = new ArrayList<>();

		try {

			String sql = "select * from feedback;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();

				feedback.setExperiencia(resultSet.getString("experiencia"));
				feedback.setImportancia(resultSet.getString("importancia"));
				feedback.setFacilidade(resultSet.getString("facilidade"));
				feedback.setSugestao(resultSet.getString("sugestao"));

				feedbacks.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return feedbacks;
	}

	public void gravarFeedback(ModelFeedback feedback) throws Exception {

		try {
			String sqlUsuario = "INSERT INTO public.feedback(id_feedback, experiencia, importancia, facilidade, sugestao) VALUES (?, ?, ?, ?, ?);";

			PreparedStatement preparedSql = connection.prepareStatement(sqlUsuario);
			preparedSql.setLong(1, feedback.getUsuarioID());
			preparedSql.setString(2, feedback.getExperiencia());
			preparedSql.setString(3, feedback.getImportancia());
			preparedSql.setString(4, feedback.getFacilidade());
			preparedSql.setString(5, feedback.getSugestao());

			preparedSql.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject consultarTotalExperiencia() throws Exception {

		JSONObject obj = new JSONObject();
		
		try {

			String sql = "SELECT experiencia, count(*) as total FROM feedback GROUP BY experiencia;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {

				String experiencia = resultSet.getString("experiencia");
				Integer total = resultSet.getInt("total");
				
				obj.put(experiencia, total);
			}
	    
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public JSONObject consultarTotalFacilidade() throws Exception {

		JSONObject obj = new JSONObject();

		try {

			String sql = "SELECT facilidade, count(*) as total FROM feedback GROUP BY facilidade;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String facilidade = resultSet.getString("facilidade");
				Integer total = resultSet.getInt("total");

				obj.put(facilidade, total);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public ArrayList<ModelFeedback> consultarImportancia() throws Exception {

		ArrayList<ModelFeedback> importancia = new ArrayList<>();

		try {

			String sql = "SELECT importancia FROM feedback;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();
				feedback.setImportancia(resultSet.getString("importancia"));

				importancia.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return importancia;
	}
	
	public JSONObject consultarSugestao() throws Exception {

		JSONObject obj = new JSONObject();

		try {

			String sql = "SELECT sugestao, id_feedback FROM feedback;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String sugestao = resultSet.getString("sugestao");
				Integer id = resultSet.getInt("id_feedback");
				
				obj.put(sugestao, id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	/*public ArrayList<ModelFeedback> consultarTotalExperiencia() throws Exception {

		ArrayList<ModelFeedback> totalExperiencia = new ArrayList<>();

		try {

			String sql = "SELECT experiencia, count(*) as total FROM feedback GROUP BY experiencia;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();

				feedback.setExperiencia(resultSet.getString("experiencia"));
				feedback.setTotal(resultSet.getInt("total"));

				totalExperiencia.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalExperiencia;
	}*/
	
	/*public ArrayList<ModelFeedback> consultarTotalFacilidade() throws Exception {

		ArrayList<ModelFeedback> totalFacilidade = new ArrayList<>();

		try {

			String sql = "SELECT facilidade, count(*) as total FROM feedback GROUP BY facilidade;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();

				feedback.setFacilidade(resultSet.getString("facilidade"));
				feedback.setTotal(resultSet.getInt("total"));

				totalFacilidade.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalFacilidade;
	}*/
	
	/*public ArrayList<ModelFeedback> consultarImportancia() throws Exception {

		ArrayList<ModelFeedback> importancia = new ArrayList<>();

		try {

			String sql = "SELECT importancia FROM feedback;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();
				feedback.setImportancia(resultSet.getString("importancia"));

				importancia.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return importancia;
	}
	
	public ArrayList<ModelFeedback> consultarSugestao() throws Exception {

		ArrayList<ModelFeedback> sugestao = new ArrayList<>();

		try {

			String sql = "SELECT sugestao FROM feedback;";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				ModelFeedback feedback = new ModelFeedback();
				feedback.setSugestao(resultSet.getString("sugestao"));

				sugestao.add(feedback);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sugestao;
	}*/
}