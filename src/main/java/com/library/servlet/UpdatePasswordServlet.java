package com.library.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.library.dao.UserDAO;

/**
 * Servlet temporaire pour mettre à jour le mot de passe d'un utilisateur
 * À SUPPRIMER après utilisation pour des raisons de sécurité
 */
@WebServlet("/update-password")
public class UpdatePasswordServlet extends HttpServlet {
	
	private final UserDAO userDao = new UserDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html><head><title>Mise à jour du mot de passe</title>");
		out.println("<script src='https://cdn.tailwindcss.com'></script></head><body>");
		out.println("<div class='min-h-screen flex items-center justify-center bg-gray-100'>");
		out.println("<div class='bg-white p-8 rounded-lg shadow-md max-w-md w-full'>");
		out.println("<h1 class='text-2xl font-bold mb-4'>Mise à jour du mot de passe</h1>");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
			boolean success = userDao.updatePassword(email, password);
			if (success) {
				out.println("<div class='bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4'>");
				out.println("✓ Mot de passe mis à jour avec succès pour: " + email);
				out.println("</div>");
				out.println("<p class='mb-4'>Vous pouvez maintenant vous connecter avec ce mot de passe.</p>");
			} else {
				out.println("<div class='bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4'>");
				out.println("✗ Erreur: Impossible de mettre à jour le mot de passe. Vérifiez que l'email existe.");
				out.println("</div>");
			}
		}
		
		out.println("<form method='GET' class='space-y-4'>");
		out.println("<div>");
		out.println("<label class='block text-gray-700 font-semibold mb-2'>Email:</label>");
		out.println("<input type='email' name='email' required class='w-full px-4 py-2 border rounded' " +
		            "placeholder='admin@library.com' value='" + (email != null ? email : "") + "'>");
		out.println("</div>");
		out.println("<div>");
		out.println("<label class='block text-gray-700 font-semibold mb-2'>Nouveau mot de passe:</label>");
		out.println("<input type='password' name='password' required class='w-full px-4 py-2 border rounded' " +
		            "placeholder='Votre nouveau mot de passe'>");
		out.println("</div>");
		out.println("<button type='submit' class='w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700'>");
		out.println("Mettre à jour le mot de passe");
		out.println("</button>");
		out.println("</form>");
		out.println("<p class='mt-4 text-sm text-gray-600'>⚠️ Supprimez cette servlet après utilisation !</p>");
		out.println("</div></div></body></html>");
	}
}

