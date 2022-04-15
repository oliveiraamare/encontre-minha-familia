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
import model.ModelUsuario;

/*controller sao as servlets ou ServletLoginController*/
@WebServlet(urlPatterns = { "/componentes/ServletLogin", "/ServletLogin" }) /* Mapeamento de URL que vem da tela */
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static DAOUsuarioRepository daoLoginRepository = new DAOUsuarioRepository();

	public ServletLogin() {
	}

	/* Recebe os dados pela url em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/* recebe os dados enviados por um formulario */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
			case "logar":
				logar(request, response);
				break;
			case "sair":
				sair(request, response);
				break;
			default:
				throw new ServletException("Invalid action parameter");
		}
	}

	private static void logar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			ModelUsuario usuario = new Gson().fromJson(request.getParameter("usuario"), ModelUsuario.class);

			String login = usuario.getLogin().toUpperCase();
			String senha = usuario.getSenha();

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				if (daoLoginRepository.validarLogin(usuario)) {

					usuario = daoLoginRepository.buscarUsuario(login);
					daoLoginRepository.atualizarAcesso(usuario.getId());

					// atributo de sessao para manter o usuario no sistema
					request.getSession().setAttribute("login", usuario.getLogin());

					ModelContexto contexto = new ModelContexto();
					contexto.setUsuario(usuario);
					//contexto.setUrlRedirecionamento("componentes/sobre-nos/sobre-nos.html");
					contexto.setUrlRedirecionamento("componentes/home/home.js");

					String json = new Gson().toJson(contexto);
					response.setStatus(200);
					response.setContentType("application/json");
					response.getWriter().write(json);

				} else {
					response.setStatus(401);
					response.getWriter().write("Informe a senha e o login corretamente!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			response.setStatus(500);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	}

	private static void sair(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			request.getSession().invalidate(); // invalida a sessao
			response.setStatus(200);

		} catch (Exception e) {
			e.printStackTrace();

			response.setStatus(500);
			response.getWriter().write("Ops! Alguma coisa deu errada! Aguarde um instante antes de tentar novamente!");
		}
	}
}