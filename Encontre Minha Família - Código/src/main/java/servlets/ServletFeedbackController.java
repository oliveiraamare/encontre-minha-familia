package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.ModelAdministrador;
import model.ModelContexto;
import model.ModelContribuidor;
import model.ModelFeedback;

@WebServlet(urlPatterns = { "/componentes/ServletFeedbackController", "/ServletFeedbackController" })
public class ServletFeedbackController extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	private static ModelAdministrador admin = new ModelAdministrador();
	private static ModelContribuidor contribuidor = new ModelContribuidor();
		
	public ServletFeedbackController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {	
		
		String action = request.getParameter("action");
		System.out.println("ação usuario controller -> " + action);
		
		System.out.println("json data -> " + request.getParameter("feedback"));
						
		ModelFeedback feedback = new Gson().fromJson(request.getParameter("feedback"), ModelFeedback.class);
		
		switch(action) {
		case "cadastrar":
			cadastrarFeedback(feedback, request, response);
			break;
		case "buscar":
			buscarFeedback(request, response);
			break;
		case "buscarChart":
			buscarFeedbackChart(request, response);
			break;
		default:
			throw new ServletException("Invalid action parameter");
		}
	}
	
	private static void cadastrarFeedback (ModelFeedback feedback, HttpServletRequest request, HttpServletResponse response) {
		
			try {
				contribuidor.gravarFeedback(feedback);
	
				response.setStatus(201);
				response.getWriter().write("Feedback cadastrado com sucesso!");
					
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	
	private static void buscarFeedback(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			ArrayList<ModelFeedback> feedbacks = new ArrayList<>();
			feedbacks = admin.buscarFeedback();
			
			ModelContexto contexto = new ModelContexto();
			contexto.setListaFeedbacks(feedbacks);

			String json = new Gson().toJson(contexto);
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarFeedbackChart(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			//admin.buscarFeedbackChart();
			
			

			String json = new Gson().toJson(admin.buscarFeedbackChart());
			response.setStatus(200);
			response.setContentType("application/json");
			response.getWriter().print(admin.buscarFeedbackChart());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}