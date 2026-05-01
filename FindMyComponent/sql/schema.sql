CREATE DATABASE IF NOT EXISTS findmycomponent;
USE findmycomponent;

CREATE TABLE if NOT EXISTS roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(20) UNIQUE NOT NULL,  -- 'ADMIN', 'BUYER', 'SELLER'
                       description VARCHAR(100),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE if NOT EXISTS users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role_id INT NOT NULL DEFAULT 3,
                       first_name VARCHAR(50),
                        last_name VARCHAR(50),
                        phone VARCHAR(20),
                        is_active BOOLEAN DEFAULT TRUE,
                        is_email_verified BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        last_login TIMESTAMP NULL,
                        FOREIGN KEY (role_id) REFERENCES roles(id)
                        );
CREATE TABLE if NOT EXISTS user_sessions (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT NOT NULL,
                               session_token VARCHAR(255) UNIQUE NOT NULL,
                               expires_at TIMESTAMP NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               INDEX idx_token (session_token),
                               INDEX idx_expires (expires_at)
);

CREATE TABLE if NOT EXISTS categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) UNIQUE NOT NULL,
                            description TEXT,
                            is_active BOOLEAN DEFAULT TRUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);