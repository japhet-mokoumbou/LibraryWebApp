package com.library.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.library.dao.UserDAO;
import com.library.model.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private final UserDAO userDao = new UserDAO();
    
    public LoginServlet() {
        super();
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = userDao.login(email, password);
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("dashboard.jsp");
		} else {
			request.setAttribute("error", "Email ou mot de passe incorrect");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
