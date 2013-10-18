package br.com.hotel.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractFilter {

	public AbstractFilter() {
		super();
	}

	protected void doLogin(ServletRequest request, ServletResponse response,
			HttpServletRequest req) throws ServletException, IOException {
		RequestDispatcher rd = req
				.getRequestDispatcher("login");
		rd.forward(request, response);
	}

	protected void accessDenied(ServletRequest request,
			ServletResponse response, HttpServletRequest req)
			throws ServletException, IOException, SecurityException, NullPointerException {
		RequestDispatcher rd = req
				.getRequestDispatcher("acessoNegado");
		rd.forward(request, response);
	}
}
