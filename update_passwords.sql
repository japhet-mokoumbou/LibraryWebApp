-- Script pour mettre à jour les mots de passe en BCrypt
-- IMPORTANT: Exécutez d'abord PasswordUtil.java pour générer les hash BCrypt

-- Exemple: Mettre à jour le mot de passe pour un utilisateur
-- Remplacez 'votre_email@example.com' par l'email de votre utilisateur
-- Remplacez le hash par celui généré par PasswordUtil

-- Pour générer un hash BCrypt, exécutez:
-- java -cp "target/classes;target/WEB-INF/lib/*" com.library.util.PasswordUtil votre_mot_de_passe

-- Exemple avec le mot de passe "admin123":
-- UPDATE `user` SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' 
-- WHERE email = 'admin@library.com';

-- Exemple avec le mot de passe "password":
-- UPDATE `user` SET password = '$2a$10$rOzJqZqZqZqZqZqZqZqZqOqZqZqZqZqZqZqZqZqZqZqZqZqZqZq' 
-- WHERE email = 'user@library.com';

-- Vérifier les mots de passe actuels (affiche les 20 premiers caractères)
SELECT email, LEFT(password, 20) as password_preview, 
       CASE 
           WHEN password LIKE '$2a$%' OR password LIKE '$2b$%' OR password LIKE '$2y$%' 
           THEN 'BCrypt (OK)' 
           ELSE 'Non hashé (À corriger)' 
       END as status
FROM `user`;

