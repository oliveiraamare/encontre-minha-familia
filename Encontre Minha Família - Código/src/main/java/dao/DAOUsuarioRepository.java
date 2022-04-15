package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.SingleConnection;
import enums.TipoUsuario;
import model.ModelAdministrador;
import model.ModelContribuidor;
import model.ModelPermissao;
import model.ModelUsuario;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnection.getConnection();
	}

	public void gravarUsuario(ModelUsuario usuario, ModelContribuidor contribuidor) throws Exception {

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
				preparedStatement.setString(6, TipoUsuario.contribuidor.toString());

				int adicionado = preparedStatement.executeUpdate();

				if (adicionado > 0) {
					connection.commit();
					gravarAcesso();
					gravarContribuidor(contribuidor);
				}

			}
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

	public void gravarContribuidor(ModelContribuidor contribuidor) throws Exception {

		try {
			
			String sqlAcesso = "INSERT INTO public.contribuidor(cidade, estado, id_contribuidor)"
					+ " VALUES (?, ?, (SELECT currval('seq_usuario')));" ;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlAcesso);
			preparedStatement.setString(1, contribuidor.getCidade());
			preparedStatement.setString(2, contribuidor.getEstado());
			preparedStatement.execute();
			
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validarLogin(ModelUsuario usuario) throws Exception {
		System.out.println(usuario.getLogin().toUpperCase() + " " + usuario.getSenha() );
		try {
			String sql = "select count(*) as count FROM usuario where login = ? AND senha = ? AND ativo = true";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getLogin().toUpperCase());
			preparedStatement.setString(2, usuario.getSenha());
			
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next() && resultSet.getInt("count") > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void atualizarAcesso(Long id) throws SQLException {

		try {
			String sqlAcesso = "UPDATE public.controle_acesso SET ultimo_acesso = CURRENT_TIMESTAMP WHERE id_controle = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sqlAcesso);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ModelUsuario buscarUsuario(String login) throws Exception {
		
		ModelUsuario usuario = new ModelUsuario();
		
		try {

			String sql = "SELECT id_usuario, login, nome, email, ativo, tipo_usuario FROM usuario where login = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, login.toUpperCase());

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				usuario.setId(resultSet.getLong("id_usuario"));
				usuario.setLogin(resultSet.getString("login"));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setEmail(resultSet.getString("email"));
				usuario.setAtivo(resultSet.getBoolean("ativo"));
				usuario.setTipoUsuario(TipoUsuario.getTipoUsuario(resultSet.getString("tipo_usuario")));
			}

			if (usuario.getTipoUsuario() == TipoUsuario.contribuidor) {
				ModelContribuidor contribuidor = new ModelContribuidor();
				contribuidor = buscarContribuidor(usuario.getId());
				usuario.setContribuidor(contribuidor);
			} else {
				ModelAdministrador admin = new ModelAdministrador();
				admin = buscarAdministrador(usuario.getId());
				usuario.setAdministrador(admin);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usuario;
	}

	public ModelContribuidor buscarContribuidor(Long id) throws Exception {
		
		ModelContribuidor contribuidor = new ModelContribuidor();
		ArrayList<ModelPermissao> permissoes = new ArrayList<ModelPermissao>();
		
		try {		
			String sql = "SELECT c.cidade, c.estado, p.nome, p.descricao FROM contribuidor as c"
					+ " FULL OUTER JOIN permissao as p on c.id_contribuidor = ? "
					+ " WHERE p.tipo_usuario = 'contribuidor';";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ModelPermissao permissao = new ModelPermissao();
				
				contribuidor.setCidade(resultSet.getString("cidade"));
				contribuidor.setEstado(resultSet.getString("estado"));
				permissao.setNome(resultSet.getString("nome"));
				permissao.setDescricao(resultSet.getString("descricao"));
				
				permissoes.add(permissao);
			}
			
			contribuidor.setPermissoes(permissoes);
	
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contribuidor;
	}
	
	public ModelAdministrador buscarAdministrador(Long id) throws Exception {
		
		ModelAdministrador admin = new ModelAdministrador();		
		ArrayList<ModelPermissao> permissoes = new ArrayList<ModelPermissao>();
		
		try {				
				
			String sql = "SELECT a.contato, p.nome, p.descricao FROM administrador as a"
					+ " FULL OUTER JOIN permissao as p on a.id_administrador = ? "
					+ " WHERE p.tipo_usuario = 'admin';";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ModelPermissao permissao = new ModelPermissao();
				
				admin.setContato(resultSet.getString("contato"));
				permissao.setNome(resultSet.getString("nome"));
				permissao.setDescricao(resultSet.getString("descricao"));
				
				permissoes.add(permissao);
			}

			admin.setPermissoes(permissoes);
			
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return admin;		
	}

	public ModelUsuario atualizarUsuario(ModelUsuario usuario, ModelContribuidor contribuidor) throws Exception {

		try {
			String sql = "UPDATE usuario SET nome=?, email=? WHERE id_usuario=?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getEmail());
			preparedStatement.setLong(3, usuario.getId());
		
			int adicionado = preparedStatement.executeUpdate();

			if (adicionado > 0) {
				connection.commit();
				atualizarContribuidor(contribuidor, usuario.getId());
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buscarUsuario(usuario.getLogin());
	}
	
	public void atualizarContribuidor( ModelContribuidor contribuidor, Long id) throws Exception {

		try {
			String sql = "UPDATE public.contribuidor SET cidade=?, estado=? WHERE id_contribuidor=?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, contribuidor.getCidade());
			preparedStatement.setString(2, contribuidor.getEstado());
			preparedStatement.setLong(3, id);
			preparedStatement.executeUpdate();

			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void deletarUsuario(Long id) throws Exception {

		try {
			String sql = "DELETE FROM public.usuario WHERE id_usuario=?;";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}