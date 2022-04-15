package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DAOUsuarioRepository;
import model.ModelAdministrador;
import model.ModelContexto;
import model.ModelUsuario;

@WebServlet(urlPatterns = { "/componentes/ServletAdministradorController", "/ServletAdministradorController" })
public class ServletAdministradorController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private static ModelAdministrador admin = new ModelAdministrador();
	
	public ServletAdministradorController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {	
		
		String action = request.getParameter("action");
		System.out.println("ação usuario controller -> " + action);
		
		System.out.println("json data -> " + request.getParameter("usuario"));
		System.out.println("json data -> " + request.getParameter("administrador"));
		
		ModelUsuario usuario = new Gson().fromJson(request.getParameter("usuario"), ModelUsuario.class);
		ModelAdministrador administrador = new Gson().fromJson(request.getParameter("administrador"),ModelAdministrador.class);
		
		switch(action) {
		case "consultarAcessos":
			consultarAcessos(request, response);
			break;
		case "habilitar":
			habilitarUsuario(usuario.getId(), request, response);
			break;
		case "desabilitar":
			desabilitarUsuario(usuario.getId(), request, response);
			break;
		case "cadastrar":
			cadastrarAdmin(usuario, request, response, administrador);
			break;
		case "atualizar":
			atualizarAdmin(usuario, request, response, administrador);
			break;
		default:
			throw new ServletException("Invalid action parameter");
		}
	}
	
	private static void cadastrarAdmin(ModelUsuario usuario, HttpServletRequest request, HttpServletResponse response, ModelAdministrador administrador)  throws IOException {
			
		try {
			if (usuario.validar(usuario) && !daoUsuarioRepository.validarLogin(usuario)) {
				//verificar como tratar a exceção vinda do banco
				admin.cadastrarAdmin(usuario, administrador);				
			
				response.setStatus(201);
				response.getWriter().write("Novo administrador cadastrado com sucesso!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			response.setStatus(500);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	}
	
	private static void consultarAcessos(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			ArrayList<ModelUsuario> usuarios = new ArrayList<>();
			usuarios = admin.consultarAcessos();
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaUsuarios(usuarios);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void habilitarUsuario(Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			admin.habilitarUsuario(id);
				
			ArrayList<ModelUsuario> usuarios = new ArrayList<>();
			usuarios = admin.consultarAcessos();
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaUsuarios(usuarios);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	 
	}
	
	private static void desabilitarUsuario(Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			admin.desabilitarUsuario(id);
			
			ArrayList<ModelUsuario> usuarios = new ArrayList<>();
			usuarios = admin.consultarAcessos();
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaUsuarios(usuarios);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}			 
	}
	
	private static void atualizarAdmin(ModelUsuario usuario, HttpServletRequest request, HttpServletResponse response, ModelAdministrador administrador) {
		
		try {
			
			ModelUsuario user = new ModelUsuario();
			user = admin.atualizarAdministrador(usuario, administrador);
			
			ModelContexto contexto = new ModelContexto();
			contexto.setUsuario(user);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}