package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.library.model.Role;
import com.library.model.User;
import com.library.util.DatabaseUtil;

public class UserDAO {
	
	public User login(String email, String plainPassword) {
		
		String sql = "SELECT u.*, r.id as role_id, r.name as role_name "+
					 "FROM `user`  u JOIN role r ON u.role_id = r.id "+
					 "WHERE u.email = ? AND u.enabled = true";
		
		System.out.println("=== DEBUG LOGIN ===");
		System.out.println("Email reçu: " + email);
		
		try (Connection conn = DatabaseUtil.getConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, email);
			
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					System.out.println("Utilisateur trouvé dans la base de données");
					
					String hashedPassword = rs.getString("password");
					System.out.println("Mot de passe hashé récupéré: " + (hashedPassword != null ? hashedPassword.substring(0, Math.min(20, hashedPassword.length())) + "..." : "NULL"));
					
					// Vérifier le mot de passe
					boolean passwordMatch = false;
					try {
						passwordMatch = BCrypt.checkpw(plainPassword, hashedPassword);
						System.out.println("Vérification BCrypt: " + passwordMatch);
					} catch (Exception e) {
						System.err.println("Erreur lors de la vérification BCrypt: " + e.getMessage());
						e.printStackTrace();
						return null;
					}
					
					if (passwordMatch) {
						System.out.println("Authentification réussie !");
						
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setEmail(rs.getString("email"));
						user.setFirstName(rs.getString("first_name"));
						user.setLastName(rs.getString("last_name"));
						user.setEnabled(rs.getBoolean("enabled"));

						Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
						user.setRole(role);

						return user;
					} else {
						System.out.println("Mot de passe incorrect");
					}
				} else {
					System.out.println("Aucun utilisateur trouvé avec cet email ou utilisateur désactivé");
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erreur SQL lors de la connexion: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Erreur inattendue: " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("=== FIN DEBUG ===");
		return null;
	}
	
	/**
	 * Met à jour le mot de passe d'un utilisateur avec un hash BCrypt
	 * Utile pour convertir les mots de passe en clair en hash BCrypt
	 * @param email L'email de l'utilisateur
	 * @param newPlainPassword Le nouveau mot de passe en clair (sera hashé)
	 * @return true si la mise à jour a réussi
	 */
	public boolean updatePassword(String email, String newPlainPassword) {
		String sql = "UPDATE `user` SET password = ? WHERE email = ?";
		
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			String hashedPassword = BCrypt.hashpw(newPlainPassword, BCrypt.gensalt());
			ps.setString(1, hashedPassword);
			ps.setString(2, email);
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Mot de passe mis à jour pour: " + email);
				return true;
			} else {
				System.out.println("Aucun utilisateur trouvé avec l'email: " + email);
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println("Erreur lors de la mise à jour du mot de passe: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Vérifie si un mot de passe dans la base est hashé avec BCrypt
	 * Un hash BCrypt commence toujours par $2a$, $2b$ ou $2y$
	 */
	public boolean isPasswordHashed(String email) {
		String sql = "SELECT password FROM `user` WHERE email = ?";
		
		try (Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, email);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String password = rs.getString("password");
					return password != null && (password.startsWith("$2a$") || 
					                            password.startsWith("$2b$") || 
					                            password.startsWith("$2y$"));
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erreur lors de la vérification: " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
}
