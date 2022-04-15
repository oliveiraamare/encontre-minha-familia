package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.ModelContexto;
import model.ModelContribuidor;
import model.ModelImagem;
import model.ModelMorador;
import model.ModelUltimaLocalidade;

@MultipartConfig
@WebServlet(urlPatterns = { "/componentes/ServletMoradorController", "/ServletMoradorController" })
public class ServletMoradorController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ModelContribuidor contribuidor = new ModelContribuidor();

	public ServletMoradorController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("ação morador controller -> " + action);

		System.out.println("json data -> " + request.getParameter("morador"));
		System.out.println("json data -> " + request.getParameter("imagem"));
		System.out.println("json data -> " + request.getParameter("ultimaLocalidade"));

		ModelMorador morador = new Gson().fromJson(request.getParameter("morador"), ModelMorador.class);
		ModelImagem imagem = new Gson().fromJson(request.getParameter("imagem"), ModelImagem.class);
		ModelUltimaLocalidade ultimaLocalidade = new Gson().fromJson(request.getParameter("ultimaLocalidade"), ModelUltimaLocalidade.class);

		switch (action) {
		case "buscar":
			buscarMorador(morador.getUsuarioId(),request, response);
			break;
		case "buscarAll":
			buscarMoradorAll(request, response);
			break;
		case "cadastrar":
			cadastrarMorador(morador, imagem, ultimaLocalidade, request, response);
			break;
		case "deletar":
			deletarMorador(morador.getId(), request, response);
			break;
		case "atualizar":
			atualizarMorador(morador, ultimaLocalidade, request, response);
			break;
		default:
			throw new ServletException("Invalid action parameter");
		}
	}

	public static void buscarMorador(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			ArrayList<ModelMorador> moradores = new ArrayList<>();
			moradores = contribuidor.buscarMorador(id);
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaMoradores(moradores);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}	
	}
	
	private static void buscarMoradorAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			ArrayList<ModelMorador> moradores = new ArrayList<>();
			moradores = contribuidor.buscarMoradorAll();
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaMoradores(moradores);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	}

	public static void cadastrarMorador(ModelMorador morador, ModelImagem imagem,
			ModelUltimaLocalidade ultimaLocalidade, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		boolean validado = morador.validar(morador);

		try {
			if (validado) {

				contribuidor.gravarMorador(morador, imagem, ultimaLocalidade);

				response.setStatus(201);
				response.getWriter().write("Morador em situação de rua cadastrado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setStatus(500);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	}
	
	private static void deletarMorador(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			contribuidor.deletarMorador(id);
			
			response.setStatus(200);
			response.getWriter().write("Morador deletado com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}	 
	}
	
	private static void atualizarMorador(ModelMorador morador, ModelUltimaLocalidade ultimaLocalidade, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 try {
			contribuidor.atualizarMorador(morador, ultimaLocalidade);
			
			response.setStatus(200);
			response.getWriter().write("Dados atualizados com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	 }
}