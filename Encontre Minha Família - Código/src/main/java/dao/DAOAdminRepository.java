package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.SingleConnection;
import enums.TipoUsuario;
import model.ModelAdministrador;
import model.ModelControleAcesso;
import model.ModelUsuario;

public class DAOAdminRepository {

	private Connection connection;
	
	private static DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public DAOAdminRepository() {
		connection = SingleConnection.getConnection();
	}
	
	public void cadastrarUsuario(ModelUsuario usuario, ModelAdministrador admin) throws Exception {

		try {
			if (usuario.validar(usuario)) {
			
				String sqlUsuario = "INSERT INTO public.usuario(login, senha, nome, email, ativo, tipo_usuario)"
						+ "VALUES (?, ?, ?, ?, ?, CAST(? AS tipo_usuario));";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sqlUsuario);

				preparedStatement.setString(1, usuario.getLogin().toUpperCase());
				preparedStatement.setString(2, usuario.getSenha());
				preparedStatement.setString(3, usuario.getNome());
				preparedStatement.setString(4, usuario.getEmail());
				preparedStatement.setBoolean(5, true);
				preparedStatement.setString(6, TipoUsuario.admin.toString());

				int adicionado = preparedStatement.executeUpdate();

				if (adicionado > 0) {
					connection.commit();
					cadastrarAdministrador(admin);
					gravarAcesso();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cadastrarAdministrador(ModelAdministrador admin) throws Exception {

		try {
			
			String sqlAcesso = "INSERT INTO public.administrador (id_administrador, contato)"
					+ "	VALUES ((SELECT currval('seq_usuario')), ?);";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlAcesso);
			preparedStatement.setString(1, admin.getContato());
			preparedStatement.execute();
			
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gravarAcesso() throws Exception {

		try {
			String sqlAcesso = "INSERT INTO public.controle_acesso(ultimo_acesso, data_criacao, id_controle)"
					+ " VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT currval('seq_usuario')));";

			PreparedStatement preparedStatement = connection.prepareStatement(sqlAcesso);
			preparedStatement.execute();
			
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ModelUsuario> consultarUsuarioAcesso() throws Exception {
		
		ArrayList<ModelUsuario> usuarios = new ArrayList<>(); 
		
		try {

			String sql = "select * from usuario u join controle_acesso acesso on u.id_usuario = acesso.id_controle";/* and useradmin is false";*/
	
			PreparedStatement statement = connection.prepareStatement(sql);
	
			ResultSet resultSet = statement.executeQuery();
	
			while (resultSet.next()) {
				
				ModelControleAcesso acesso = new ModelControleAcesso();
				ModelUsuario usuario = new ModelUsuario();
				
				acesso.setUltimoAcesso(resultSet.getTimestamp("ultimo_acesso"));
				acesso.setDataCriacao(resultSet.getTimestamp("data_criacao"));
				
				usuario.setId(resultSet.getLong("id_usuario"));
				usuario.setLogin(resultSet.getString("login"));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setEmail(resultSet.getString("email"));
				usuario.setTipoUsuario(TipoUsuario.getTipoUsuario(resultSet.getString("tipo_usuario")));
				usuario.setAtivo(resultSet.getBoolean("ativo"));
				usuario.setAcesso(acesso);
				
				usuarios.add(usuario);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usuarios;
	}
	
	public void habilitarUsuario(Long id) throws Exception {

		try {
				String sqlUsuario = "UPDATE public.usuario SET ativo = ? WHERE id_usuario = ?;";
				
				PreparedStatement preparedSql = connection.prepareStatement(sqlUsuario);
				preparedSql.setBoolean(1, true);
				preparedSql.setLong(2, id);
				preparedSql.execute();
				
				connection.commit();
					
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void desabilitarUsuario(Long id) throws Exception {

		try {
				String sqlUsuario = "UPDATE public.usuario SET ativo = ? WHERE id_usuario = ?;";
				
				PreparedStatement preparedSql = connection.prepareStatement(sqlUsuario);
				preparedSql.setBoolean(1, false);
				preparedSql.setLong(2, id);
				preparedSql.execute();
				
				connection.commit();
					
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void gravarControleAcesso(String login) throws Exception {
		
		try {
			String sqlAcesso =  "INSERT INTO controle_acesso (id_acesso, ultimo_acesso, ativo, data_criacao)"
					+ " values ((SELECT id_usuario from usuario WHERE login='" + login + "' ), CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP);";
			
			PreparedStatement statement = connection.prepareStatement(sqlAcesso);
			statement.setBoolean(1, true);
			statement.execute();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ModelUsuario atualizarUsuario(ModelUsuario user, ModelAdministrador admin) throws Exception {

		try {
			String sql = "UPDATE usuario SET email=?, nome=? WHERE id_usuario=?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getNome());
			preparedStatement.setLong(3, user.getId());
						
			int atualizado = preparedStatement.executeUpdate();

			if (atualizado > 0) {
				connection.commit();
				atualizarAdmin(user.getId(), admin);
			}
			
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return daoUsuarioRepository.buscarUsuario(user.getLogin());
	}
	
	public void atualizarAdmin(Long id, ModelAdministrador admin) throws SQLException {

		try {
			String sqlAcesso = "UPDATE public.administrador SET contato = ? WHERE id_administrador = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sqlAcesso);
			preparedStatement.setString(1, admin.getContato());
			preparedStatement.setLong(2, id);
			preparedStatement.execute();
			
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
}