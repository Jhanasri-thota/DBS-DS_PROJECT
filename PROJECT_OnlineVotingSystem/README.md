# VoteSecure – Secure Online Voting System

Academic/demo full-stack DBMS project using React 19 + Vite, Java 21 + Spring Boot 3.5.5, and MySQL 8.

## Important credential rule
There are **NO default usernames or passwords** in the database. Eight pre-enrolled member profiles contain only voter information. A person must use the Register page to create their own Voter ID/username and password. Passwords are stored as BCrypt hashes. Those exact credentials are then used on Login.

The OTP `123456` is only a local academic/demo OTP; no real Aadhaar/UIDAI/SMS integration exists.

## Database
1. Open MySQL Workbench.
2. Run `database/schema.sql`.
3. The script creates `online_voting`, election data, candidates, votes, OTP records, audit logs, and 8 pre-enrolled voter profiles with **no credentials**.
4. In `backend/src/main/resources/application.properties`, set `spring.datasource.password` to your own MySQL password.

## Backend
```powershell
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

## Frontend
```powershell
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Registration flow
Register → enter member information → demo identity ID → OTP 123456 → choose your own Voter ID and password → account saved to MySQL → Login with the newly created credentials → OTP → Dashboard → Vote.

## Main backend APIs
POST `/api/auth/register`
POST `/api/auth/login`
POST `/api/auth/verify-otp`
GET `/api/election`
GET `/api/candidates`
GET `/api/results`
POST `/api/votes`
GET `/api/health`
GET `/api/stats`

## Project tables
`election_settings`, `voters`, `candidates`, `votes`, `otp_verifications`, `audit_logs`.

This is an academic demonstration system and not a real government election platform.
