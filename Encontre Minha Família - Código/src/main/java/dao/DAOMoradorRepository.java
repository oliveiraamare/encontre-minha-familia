package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.SingleConnection;
import model.ModelImagem;
import model.ModelMorador;
import model.ModelUltimaLocalidade;

public class DAOMoradorRepository {

	private Connection connection;

	public DAOMoradorRepository() {
		connection = SingleConnection.getConnection();
	}

	public ModelMorador gravarMorador(ModelMorador morador, ModelImagem imagem, ModelUltimaLocalidade ultimaLocalidade)
			throws Exception {

		if (morador.validar(morador)) {

			String sql = "INSERT INTO public.morador("
					+ " nome_morador, idade, genero, cidade_origem, contato, aparencia, sitacao, estado_origem, id_contribuidor, data_cadastro)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);";
			
			/*SELECT id_morador, nome_morador, idade, genero, cidade_origem, contato, aparencia, data_cadastro, 
			sitacao, estado_origem, id_contribuidor, extensao_imagem, imagem, estado, cidade, bairro, local_visto, ponto_referencia
			FROM public.morador;*/ 
			//criar opção para excluir, criar opção para dizer que foi encontrado pela família, criar opção para atualizar informações

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, morador.getNome());
			preparedStatement.setString(2, morador.getIdade());
			preparedStatement.setString(3, morador.getGenero());
			preparedStatement.setString(4, morador.getCidade());
			preparedStatement.setString(5, morador.getContato());
			preparedStatement.setString(6, morador.getAparencia());
			preparedStatement.setString(7, morador.getSituacao());
			preparedStatement.setString(8, morador.getEstado());
			preparedStatement.setLong(9, morador.getUsuarioId());

			int adicionado = preparedStatement.executeUpdate();

			if (adicionado > 0) {
				connection.commit();
				gravarImagem(imagem);
				gravarUltimaLocalidade(ultimaLocalidade);
			}
		}

		return morador;
	}

	public void gravarImagem(ModelImagem imagem) throws Exception {

		try {
			String sql = "INSERT INTO public.imagem(id_imagem, extensao_imagem, imagem) VALUES ((SELECT currval('seq_morador')), ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, imagem.getExtensaoImagem());
			preparedStatement.setString(2, imagem.getImagem());
			preparedStatement.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gravarUltimaLocalidade(ModelUltimaLocalidade ultimaLocalidade) throws Exception {

		try {
			String sql = "INSERT INTO public.ultima_localidade(id_local, estado, cidade, bairro, local_visto, ponto_referencia) VALUES ((SELECT currval('seq_morador')), ?, ?, ?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ultimaLocalidade.getEstado());
			preparedStatement.setString(2, ultimaLocalidade.getCidade());
			preparedStatement.setString(3, ultimaLocalidade.getBairro());
			preparedStatement.setString(4, ultimaLocalidade.getLocalVisto());
			preparedStatement.setString(5, ultimaLocalidade.getPontoReferencia());
			preparedStatement.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ModelMorador> buscarMorador(Long id) throws Exception {

		ArrayList<ModelMorador> moradores = new ArrayList<>();

		try {

			String sql = "SELECT id_morador, nome_morador, idade, genero, cidade_origem, contato, aparencia, sitacao, estado_origem, "
					+ " extensao_imagem, imagem, estado, cidade, bairro, local_visto, ponto_referencia FROM morador m"
					+ " JOIN  imagem i ON m.id_morador = i.id_imagem"
					+ "	JOIN ultima_localidade u ON m.id_morador = u.id_local"
					+ " WHERE m.id_contribuidor = ? ORDER BY nome_morador ASC;";																											 

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				ModelMorador morador = new ModelMorador();
				ModelImagem imagem = new ModelImagem();
				ModelUltimaLocalidade local = new ModelUltimaLocalidade();

				imagem.setExtensaoImagem(resultSet.getString("extensao_imagem"));
				imagem.setImagem(resultSet.getString("imagem"));
				
				local.setBairro(resultSet.getString("bairro"));
				local.setCidade(resultSet.getString("cidade"));
				local.setEstado(resultSet.getString("estado"));
				local.setLocalVisto(resultSet.getString("local_visto"));
				local.setPontoReferencia(resultSet.getString("ponto_referencia"));

				morador.setId(resultSet.getLong("id_morador"));
				morador.setNome(resultSet.getString("nome_morador"));
				morador.setIdade(resultSet.getString("idade"));
				morador.setGenero(resultSet.getString("genero"));
				morador.setCidade(resultSet.getString("cidade_origem"));
				morador.setContato(resultSet.getString("contato"));
				morador.setAparencia(resultSet.getString("aparencia"));
				morador.setEstado(resultSet.getString("estado_origem"));
				
				String situacao = resultSet.getString("sitacao");
				if(situacao.equals("utiliza_abrigo")) {
					situacao = "utiliza abrigo";
				} else if (situacao.equals("em_situacao_rua")) {
					situacao = "em situação de rua";
				} else {
					situacao = "desconhecido";
				}
				
				morador.setSituacao(situacao);
				
				morador.setImagem(imagem);
				morador.setUltimalocalidade(local);				

				moradores.add(morador);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return moradores;
	}
	

	public ArrayList<ModelMorador> buscarMoradorAll() throws Exception {

		ArrayList<ModelMorador> moradores = new ArrayList<>();

		try {

			String sql = "SELECT id_morador, nome_morador, idade, genero, cidade_origem, contato, aparencia, sitacao, estado_origem, "
					+ " extensao_imagem, imagem, estado, cidade, bairro, local_visto, ponto_referencia FROM morador m"
					+ " JOIN  imagem i ON m.id_morador = i.id_imagem"
					+ "	JOIN ultima_localidade u ON m.id_morador = u.id_local"
					+ " ORDER BY nome_morador ASC;";		
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				ModelMorador morador = new ModelMorador();
				ModelImagem imagem = new ModelImagem();
				ModelUltimaLocalidade local = new ModelUltimaLocalidade();

				imagem.setExtensaoImagem(resultSet.getString("extensao_imagem"));
				imagem.setImagem(resultSet.getString("imagem"));
				
				local.setBairro(resultSet.getString("bairro"));
				local.setCidade(resultSet.getString("cidade"));
				local.setEstado(resultSet.getString("estado"));
				local.setLocalVisto(resultSet.getString("local_visto"));
				local.setPontoReferencia(resultSet.getString("ponto_referencia"));

				morador.setId(resultSet.getLong("id_morador"));
				morador.setNome(resultSet.getString("nome_morador"));
				morador.setIdade(resultSet.getString("idade"));
				morador.setGenero(resultSet.getString("genero"));
				morador.setCidade(resultSet.getString("cidade_origem"));
				morador.setContato(resultSet.getString("contato"));
				morador.setAparencia(resultSet.getString("aparencia"));
				morador.setEstado(resultSet.getString("estado_origem"));
				
				String situacao = resultSet.getString("sitacao");
				if(situacao.equals("utiliza_abrigo")) {
					situacao = "utiliza abrigo";
				} else if (situacao.equals("em_situacao_rua")) {
					situacao = "em situação de rua";
				} else {
					situacao = "desconhecido";
				}
				
				morador.setSituacao(situacao);
				
				morador.setImagem(imagem);
				morador.setUltimalocalidade(local);				

				moradores.add(morador);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return moradores;
	}
	
	public void deletarMorador(Long id) throws Exception {

		try {
			String sql = "DELETE FROM public.morador WHERE id_morador=?;";

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
	
	public void atualizarMorador(ModelMorador morador, ModelUltimaLocalidade ultimaLocalidade) throws Exception {

		try {
			String sql = "UPDATE public.morador SET nome_morador=?, idade=?, genero=?, cidade_origem=?, contato=?, aparencia=?, sitacao=?, estado_origem=?"
					+ " WHERE id_morador=?;";
					
				
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, morador.getNome());
			preparedStatement.setString(2, morador.getIdade());
			preparedStatement.setString(3, morador.getGenero());
			preparedStatement.setString(4, morador.getCidade());
			preparedStatement.setString(5, morador.getContato());
			preparedStatement.setString(6, morador.getAparencia());
			preparedStatement.setString(7, morador.getSituacao());
			preparedStatement.setString(8, morador.getEstado());
			preparedStatement.setLong(9, morador.getId());
		
			int adicionado = preparedStatement.executeUpdate();

			if (adicionado > 0) {
				connection.commit();
				atualizarUltimaLocalidade(ultimaLocalidade, morador.getId());
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void atualizarUltimaLocalidade(ModelUltimaLocalidade ultimaLocalidade, Long id) throws Exception {

		try {
			String sql = "UPDATE public.ultima_localidade SET estado=?, cidade=?, bairro=?, local_visto=?, ponto_referencia=? "
					+ " WHERE id_local=?;";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ultimaLocalidade.getEstado());
			preparedStatement.setString(2, ultimaLocalidade.getCidade());
			preparedStatement.setString(3, ultimaLocalidade.getBairro());
			preparedStatement.setString(4, ultimaLocalidade.getLocalVisto());
			preparedStatement.setString(5, ultimaLocalidade.getPontoReferencia());
			preparedStatement.setLong(6, id);
		
			preparedStatement.executeUpdate();

			connection.commit();
	
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}