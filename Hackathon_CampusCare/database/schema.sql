CREATE DATABASE IF NOT EXISTS campuscare
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campuscare;

-- Spring Boot/JPA creates and updates the application tables on startup.
-- This file seeds only the standard service departments.
CREATE TABLE IF NOT EXISTS departments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  description VARCHAR(500)
);

INSERT IGNORE INTO departments(name, description) VALUES
('IT Support','Wi-Fi, internet, computer labs and software'),
('Electrical','Lighting, power and electrical maintenance'),
('Plumbing','Water supply and plumbing issues'),
('Housekeeping','Cleaning and sanitation'),
('Transport','Campus transport and parking'),
('Hostel','Hostel facilities and maintenance'),
('Infrastructure','Classrooms, furniture and buildings'),
('Campus Administration','General campus services');
