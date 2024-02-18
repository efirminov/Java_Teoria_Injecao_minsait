package br.com.efirminov.webframeword.web;

import java.io.IOException;

import br.com.efirminov.webframeword.util.WebFrameworkLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebFrameworkDispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ignorar o favIcon:
		if (req.getRequestURL().toString().endsWith("/favicon.ico"))
			return;

		String url = req.getRequestURL().toString();
		String httpMethod = req.getMethod().toUpperCase(); // GET POST

		WebFrameworkLogger.log("WebFrameworkDispatcherServlet", "URL: " + url + "(" + httpMethod + ")");
	}

}
