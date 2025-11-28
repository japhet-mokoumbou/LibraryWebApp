package com.library.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilitaire pour gérer les mots de passe avec BCrypt
 * Utilisez cette classe pour générer des hash de mots de passe à insérer dans la base de données
 */
public class PasswordUtil {
	
	/**
	 * Génère un hash BCrypt pour un mot de passe en clair
	 * @param plainPassword Le mot de passe en clair
	 * @return Le hash BCrypt
	 */
	public static String hashPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
	
	/**
	 * Vérifie si un mot de passe en clair correspond à un hash BCrypt
	 * @param plainPassword Le mot de passe en clair
	 * @param hashedPassword Le hash BCrypt
	 * @return true si le mot de passe correspond
	 */
	public static boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
	
	/**
	 * Méthode main pour tester/générer des hash
	 * Exécutez cette classe pour générer un hash pour un mot de passe
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			String password = args[0];
			String hash = hashPassword(password);
			System.out.println("Mot de passe: " + password);
			System.out.println("Hash BCrypt: " + hash);
			System.out.println("\nSQL pour insérer cet utilisateur:");
			System.out.println("INSERT INTO `user` (email, password, first_name, last_name, role_id, enabled)");
			System.out.println("VALUES ('votre_email@example.com', '" + hash + "', 'Prénom', 'Nom', 1, TRUE);");
		} else {
			System.out.println("Usage: java PasswordUtil <mot_de_passe>");
			System.out.println("Exemple: java PasswordUtil admin123");
		}
	}
}

