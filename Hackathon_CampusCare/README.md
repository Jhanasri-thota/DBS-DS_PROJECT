# CampusCure – Campus Complaint Management
A fresh full-stack campus complaint portal.

## Stack
- React + Vite frontend
- Java 21 + Spring Boot 3.4.5 backend
- MySQL 8 database
- BCrypt password hashing
- 6-digit random OTP after University ID/password verification

## Roles and access
Register as Admin, Faculty, Staff or Student. All signed-in roles can access the same application pages; this demo intentionally has no role-based page restrictions.

## Run it
1. Create database in MySQL: `CREATE DATABASE campuscure;`
2. Edit `backend/src/main/resources/application.properties` and set your MySQL password.
3. Terminal 1: `cd backend` then `mvn spring-boot:run`
4. Terminal 2: `cd frontend` then `npm install` then `npm run dev`
5. Open `http://localhost:5173`.

## OTP demo warning
For local demonstration only, the generated OTP is returned by the API and displayed on screen. It is not sent by SMS/email. Before real deployment, integrate an OTP provider and never expose OTP values in API responses. This project is a hackathon starter, not production-ready.
