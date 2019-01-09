package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DamerauLevenshtein;
import util.DamerauLevenstein;

/**
 * Servlet implementation class LevensteinTest
 */
@WebServlet("/LevensteinTest")
public class LevensteinTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LevensteinTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String origem, alvo;
		origem = request.getParameter("origem");
		alvo = request.getParameter("alvo");
		
		
		PrintWriter out = response.getWriter();
		out.append("Dist�ncia entre '" + origem + "' e '" + alvo + "' �: " + String.valueOf(DamerauLevenshtein.calculateDistance(origem, alvo)));
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
