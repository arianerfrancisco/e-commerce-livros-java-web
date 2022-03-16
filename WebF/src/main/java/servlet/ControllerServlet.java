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
		path = path.substring(1, path.indexOf(".")); // irá ler a partir do indice 1, ou seja, irá retirar a \ e até a primeiro ponto. 
		// Ou seja de /ProcessarLivro. irá ler ProcessarLivro para assim encontrar a action responsável no arquivo ("/action.properties")
		
		String actionClass = actions.getProperty(path); // uma vez encontrado o path, é possível então encontrar a classe corresponde
		
		if(actionClass == null) {
			throw new ServletException("A action '" + path + "' não está mapeada");
		}
			
		try {
			Action action = (Action) Class.forName(actionClass).newInstance();
			//Class.forName: retorna a classe de um determinado objeto em tempo de execução
			// Quando é conhecido o nome da classe e o pacote que está contida, utiltiza Class.forName
			// (Action) está fazendo um casting para Action
			action.setRequest(request);
			action.setResponse(response);
			action.runAction();
		
		} catch (Exception e) {
			request.setAttribute("exception", e);
			throw new ServletException(e);
		}
	}
}
