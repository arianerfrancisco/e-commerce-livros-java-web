package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;

@WebServlet("*.action")
public class ControllerServlet extends HttpServlet {
	
	private static Properties actions;
	
	static {
		try {
			InputStream is = ControllerServlet.class.getResourceAsStream("/action.properties");
			actions = new Properties();
			actions.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String path = request.getServletPath();
		path = path.substring(1, path.indexOf(".")); // ir� ler a partir do indice 1, ou seja, ir� retirar a \ e at� a primeiro ponto. 
		// Ou seja de /ProcessarLivro. ir� ler ProcessarLivro para assim encontrar a action respons�vel no arquivo ("/action.properties")
		
		String actionClass = actions.getProperty(path); // uma vez encontrado o path, � poss�vel ent�o encontrar a classe corresponde
		
		if(actionClass == null) {
			throw new ServletException("A action '" + path + "' n�o est� mapeada");
		}
			
		try {
			Action action = (Action) Class.forName(actionClass).newInstance();
			//Class.forName: retorna a classe de um determinado objeto em tempo de execu��o
			// Quando � conhecido o nome da classe e o pacote que est� contida, utiltiza Class.forName
			// (Action) est� fazendo um casting para Action
			action.setRequest(request);
			action.setResponse(response);
			action.runAction();
		
		} catch (Exception e) {
			request.setAttribute("exception", e);
			throw new ServletException(e);
		}
	}
}
