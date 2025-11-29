package com.library.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"*.jsp", "/servlet/*", "/books"}, servletNames = {"LoginServlet"})
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
			
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// On récupère la session seulement si elle existe
		HttpSession session = request.getSession(false);
		
		String loginURI = request.getContextPath() + "/login.jsp";
		String requestURI = request.getRequestURI();

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = requestURI.equals(loginURI) || requestURI.endsWith("login");

        if (loggedIn || loginRequest || requestURI.contains("/resources/")) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
	}
	
	
}
