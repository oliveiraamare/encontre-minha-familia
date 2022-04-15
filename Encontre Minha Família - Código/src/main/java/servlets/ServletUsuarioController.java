package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DAOUsuarioRepository;
import model.ModelContexto;
import model.ModelContribuidor;
import model.ModelUsuario;

@WebServlet(urlPatterns = { "/componentes/ServletUsuarioController", "/ServletUsuarioController" })
public class ServletUsuarioController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private static ModelContribuidor contrib = new ModelContribuidor();
	
	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
			
		String action = request.getParameter("action");
		System.out.println("ação usuario controller -> " + action);
		
		System.out.println("json data -> " + request.getParameter("usuario"));
		System.out.println("json data -> " + request.getParameter("contribuidor"));

		ModelUsuario usuario = new Gson().fromJson(request.getParameter("usuario"), ModelUsuario.class);
		ModelContribuidor contribuidor = new Gson().fromJson(request.getParameter("contribuidor"), ModelContribuidor.class);
		
		switch(action) {
			case "atualizar":
				atualizarContribuidor(usuario, contribuidor, request, response);
				break;
			case "cadastrar":
				cadastrarUsuario(usuario, contribuidor, request, response);
				break;
			case "deletar":
				deletarUsuario(usuario.getId(), request, response);
				break;
			case "buscar":
				buscarUsuario(usuario);
				break;
			default:
				throw new ServletException("Invalid action parameter");
		}		
	}
	
	 private static void atualizarContribuidor(ModelUsuario usuario, ModelContribuidor contribuidor, HttpServletRequest request, HttpServletResponse response) {
		 try {
			usuario = contrib.atualizarContribuidor(usuario, contribuidor);
			
			ModelContexto contexto = new ModelContexto();
			contexto.setUsuario(usuario);
		
			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 private static void cadastrarUsuario(ModelUsuario usuario, ModelContribuidor contribuidor, HttpServletRequest request, HttpServletResponse response) {
	
			try {
					if (usuario.validar(usuario) && !daoUsuarioRepository.validarLogin(usuario)) {
						
						daoUsuarioRepository.gravarUsuario(usuario, contribuidor);
						usuario = daoUsuarioRepository.buscarUsuario(usuario.getLogin());
		
						// atributo de sessao para manter o usuario no sistema
						request.getSession().setAttribute("login", usuario.getLogin());
		
						ModelContexto contexto = new ModelContexto();
						contexto.setUsuario(usuario);
						contexto.setUrlRedirecionamento("componentes/home/home.js");
		
						String json = new Gson().toJson(contexto);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(json);
					}//colocar else com erro
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 
	private static void deletarUsuario(Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			daoUsuarioRepository.deletarUsuario(id);
			
			// atributo de sessao para manter o usuario no sistema
			request.getSession().removeAttribute("login");

			ModelContexto contexto = new ModelContexto();
			contexto.setUrlRedirecionamento("login/login.js");

			String json = new Gson().toJson(contexto);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	 
	}
	
	private static ModelUsuario buscarUsuario(ModelUsuario usuario) {
		try {
			usuario = daoUsuarioRepository.buscarUsuario(usuario.getLogin());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

}
