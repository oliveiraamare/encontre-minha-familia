package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.SingleConnection;

@WebFilter(urlPatterns = {"/componentes/*" }) /* Interceptas todas as requisiçoes que vierem do projeto ou mapeamento */
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {

	}

	/* encerra os processos quando o servidor é parado */
	/* exemplo: mata o processo de conexão com o banco */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* intercepta as requisições e as respostas no sistema */
	/* tudo o que fizer no sistema vai fazer por aqui */
	/*
	 * exemplos: validação de autenticação; dar commit e rollback de transações no
	 * banco; validar e fazer redirecionamento de páginas
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpSession session = httpServletRequest.getSession();

			String usuarioLogado = (String) session.getAttribute("login");
			String urlAutenticar = httpServletRequest.getServletPath();

			System.out.println("urls para verificar -> 1: " + httpServletRequest.getServletPath() + " 2: "
					+ httpServletRequest.getRequestURL().toString());

			/* validar se está logado caso não redireciona para login */
			if (usuarioLogado == null && !urlAutenticar.equalsIgnoreCase("/componentes/ServletLogin")) // *não está
																										// logado*
			{
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect("/encontre-minha-familia"); // Redirect to home page.

				/* para a execução e redireciona para o login */
				return;

			} else {
				// pass the request along the filter chain
				System.out.println("do chain");
				chain.doFilter(request, response);
			}

			connection.commit();

		} catch (Exception e) {

			e.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}

	/*
	 * executado quando inicia o sistema, inicia os processos ou recursos quando o
	 * servidor der o start
	 */
	/* iniciar a conexão com o banco */
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}
}