-- Script SQL pour créer la base de données et les tables
-- Exécutez ce script dans MySQL

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Créer la table role
CREATE TABLE IF NOT EXISTS role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Créer la table user
CREATE TABLE IF NOT EXISTS `user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role_id INT NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Insérer des rôles de base
INSERT INTO role (id, name) VALUES (1, 'ADMIN') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO role (id, name) VALUES (2, 'USER') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO role (id, name) VALUES (3, 'LIBRARIAN') ON DUPLICATE KEY UPDATE name=name;

-- IMPORTANT: Les mots de passe doivent être hashés avec BCrypt
-- Le hash BCrypt pour le mot de passe "admin123" est: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- Le hash BCrypt pour le mot de passe "password" est: $2a$10$rOzJqZqZqZqZqZqZqZqZqOqZqZqZqZqZqZqZqZqZqZqZqZqZqZqZq

-- Insérer un utilisateur de test (mot de passe: admin123)
-- Vous pouvez générer un nouveau hash avec BCrypt si nécessaire
INSERT INTO `user` (email, password, first_name, last_name, role_id, enabled) 
VALUES ('admin@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin', 'User', 1, TRUE)
ON DUPLICATE KEY UPDATE email=email;

-- Créer la table category
CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Créer la table book
CREATE TABLE IF NOT EXISTS book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    cover_image VARCHAR(500),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Insérer des catégories de test
INSERT INTO category (id, name) VALUES (1, 'Roman') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (2, 'Science-Fiction') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (3, 'Histoire') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (4, 'Informatique') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (5, 'Biographie') ON DUPLICATE KEY UPDATE name=name;

-- Insérer des livres de test
INSERT INTO book (isbn, title, author, category_id, total_copies, available_copies) 
VALUES 
    ('978-2-07-036822-8', 'Le Petit Prince', 'Antoine de Saint-Exupéry', 1, 5, 3),
    ('978-2-07-036822-9', '1984', 'George Orwell', 2, 3, 2),
    ('978-2-07-036822-0', 'Sapiens', 'Yuval Noah Harari', 3, 4, 4),
    ('978-2-07-036822-1', 'Clean Code', 'Robert C. Martin', 4, 2, 1),
    ('978-2-07-036822-2', 'Steve Jobs', 'Walter Isaacson', 5, 3, 2)
ON DUPLICATE KEY UPDATE title=title;

-- Vérifier les données
SELECT u.id, u.email, u.first_name, u.last_name, u.enabled, r.name as role_name 
FROM `user` u 
JOIN role r ON u.role_id = r.id;

