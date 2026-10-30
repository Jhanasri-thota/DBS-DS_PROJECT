# CampusCare — Simple Full-Stack Campus Complaint Management

A straightforward, AI/ML-free starter project using:
- React + Vite frontend
- Java 21 + Spring Boot 3.4.5 backend
- MySQL 8 database
- Spring Data JPA, Bean Validation, BCrypt password hashing
- Simple database-backed token authentication

## Requirements
- Node.js 20+
- Java 21
- Maven 3.9+
- MySQL 8+

## 1. Create the database
Open MySQL Workbench and run all of `database/schema.sql`.

The backend also creates tables automatically (`ddl-auto=update`), but the SQL script creates the database and recommended initial departments.

## 2. Configure the backend
Open `backend/src/main/resources/application.properties`.
Set `spring.datasource.username` and `spring.datasource.password` to your MySQL credentials.

## 3. Start backend (Terminal 1)
```powershell
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

## 4. Start frontend (Terminal 2)
```powershell
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## 5. Create the first Admin
Open:
http://localhost:5173/setup-admin

The setup endpoint works only while there is no ADMIN account. Create your own admin name, email and password. No demo credentials are included.

## User workflow
- Student: registration is automatically approved.
- Faculty and Staff: registration is pending until Admin approves.
- Admin: approves/rejects Faculty and Staff, manages users and departments, views complaints and reports.
- Student/Faculty: submit complaints and track status.
- Admin: assigns complaints to Staff.
- Staff: updates assigned complaint status and resolution remarks.
- Users: submit feedback after a complaint is resolved.

## Notes
This is a local-development/hackathon project, not a production deployment. It uses opaque random bearer tokens stored in the database. For production, add HTTPS, token expiry/refresh, email verification, rate limiting, audit review, backups and deployment secrets.
