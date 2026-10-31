# 🏫 CampusCure

### Smart Campus Complaint & Management System

<p align="center">
  <b>Your Campus. Your Voice. Our Responsibility.</b>
  <br>
  A Full-Stack Web Application for Campus Complaint Registration and Tracking
</p>

---

## 📌 1. Project Overview

**CampusCure** is a full-stack Campus Complaint Management System designed to simplify the process of reporting, managing and tracking complaints within an educational institution.

In many educational institutions, students, faculty and staff face difficulties reporting campus-related problems such as electrical issues, network failures, plumbing problems, cleanliness concerns and classroom maintenance issues.

Traditional complaint-handling methods often involve manual communication, paperwork and delayed responses.

CampusCure provides a centralized digital platform where users can register, log in, submit complaints, monitor complaint status and view campus service updates.

The application provides a common dashboard with complaint statistics and management options to improve transparency and simplify campus issue tracking.

---

## 🚨 2. Problem Statement

Traditional campus complaint management processes face several challenges:

* Manual complaint registration.
* Difficulty tracking complaint progress.
* Delayed communication regarding campus issues.
* Lack of centralized complaint information.
* Difficulty monitoring unresolved complaints.
* Limited visibility into complaint statistics.

### 💡 Proposed Solution

CampusCure provides a centralized web-based platform that allows campus users to report problems digitally and monitor their progress through a common complaint dashboard.

The system stores complaint information in a MySQL database and provides APIs to manage complaint registration, status updates and assignments.

---

## 🎯 3. Project Objectives

1. Develop a centralized campus complaint management platform.
2. Provide a simple registration and login system.
3. Implement University ID and password-based authentication.
4. Add six-digit OTP verification during login.
5. Allow users to submit campus-related complaints.
6. Organize complaints using categories and priority levels.
7. Track complaint statuses through a dashboard.
8. Display complaint statistics.
9. Store user and complaint information in MySQL.
10. Improve transparency and accessibility in campus issue reporting.

---

## ✨ 4. Key Features

### 🔐 User Registration and Authentication

* Register using University ID.
* Enter full name and email address.
* Create a password.
* Select an account type:

  * Admin
  * Faculty
  * Staff
  * Student
* Password hashing using BCrypt.
* Login using University ID and password.
* Random six-digit OTP verification.

### 📝 Complaint Management

Users can submit campus complaints with:

* Complaint title.
* Detailed description.
* Complaint category.
* Priority level.
* Campus location.

Available categories:

* Electrical
* Network
* Plumbing
* Cleanliness
* Furniture
* Classroom
* Lab Equipment
* Security
* Other

Available priority levels:

* Low
* Medium
* High
* Critical

### 📊 Dashboard

The dashboard displays:

* Total complaints.
* Submitted complaints.
* In-progress complaints.
* Resolved complaints.

Users can also:

* View registered complaints.
* Check complaint details.
* View reporter University IDs.
* Refresh complaint information.
* Update complaint statuses.

### 🔄 Complaint Status Management

Complaints support the following statuses:

1. Submitted
2. Assigned
3. In Progress
4. Resolved
5. Closed

### 👥 Multiple User Account Types

The registration system supports four account types:

* Admin
* Faculty
* Staff
* Student

All signed-in account types can access the same application pages in this demonstration version. Separate role-based page restrictions are not implemented.

---

## 🛠️ 5. Technology Stack

| Component               | Technology         |
| ----------------------- | ------------------ |
| Frontend                | React.js           |
| Build Tool              | Vite               |
| Programming Language    | JavaScript         |
| UI Icons                | Lucide React       |
| API Communication       | Axios              |
| Backend                 | Java 21            |
| Backend Framework       | Spring Boot 3.4.5  |
| REST API                | Spring Web         |
| ORM                     | Spring Data JPA    |
| Password Hashing        | BCrypt             |
| Input Validation        | Jakarta Validation |
| Database                | MySQL 8            |
| Database Management     | MySQL Workbench    |
| Backend Build Tool      | Maven              |
| Version Control         | Git                |
| Code Hosting            | GitHub             |
| Development Environment | Visual Studio Code |

---

## 🏗️ 6. System Architecture

CampusCure follows a frontend-backend-database architecture.

```text
                  CAMPUSCURE
                      |
          +-----------+-----------+
          |                       |
       FRONTEND                BACKEND
          |                       |
       React.js                Java 21
          |                       |
         Vite                Spring Boot
          |                       |
          |                   REST APIs
          |                       |
          |                Spring Data JPA
          |                       |
          |                    MySQL
          |                       |
          +-----------------------+
```

### Frontend

The React application provides the user interface for registration, authentication, complaint submission and dashboard operations.

### Backend

The Spring Boot application provides REST APIs for authentication, OTP verification and complaint management.

### Database

MySQL stores registered user information and campus complaint records.

---

## 🔄 7. Application Workflow

```text
              START APPLICATION
                      |
                USER REGISTRATION
                      |
                 USER LOGIN
                      |
           UNIVERSITY ID + PASSWORD
                      |
              PASSWORD VERIFICATION
                      |
              SIX-DIGIT OTP
                      |
               OTP VERIFICATION
                      |
                 DASHBOARD
                      |
             SUBMIT NEW COMPLAINT
                      |
            SAVE COMPLAINT IN MYSQL
                      |
              VIEW COMPLAINTS
                      |
             UPDATE COMPLAINT STATUS
                      |
             REFRESH DASHBOARD
                      |
                     END
```

---

## 🗄️ 8. Database Design

Database Name: `campuscure`

The backend uses Spring Data JPA and Hibernate to manage the database.

### Table 1: app_users

Stores registered user information.

| Column        | Description                      |
| ------------- | -------------------------------- |
| id            | Unique user ID                   |
| university_id | University identification number |
| full_name     | Registered user's name           |
| email         | User email address               |
| password_hash | BCrypt-hashed password           |
| role          | User account type                |

### Table 2: complaints

Stores campus complaint information.

| Column      | Description                      |
| ----------- | -------------------------------- |
| id          | Unique complaint ID              |
| title       | Complaint title                  |
| description | Complaint details                |
| category    | Complaint category               |
| priority    | Complaint priority               |
| status      | Current complaint status         |
| location    | Campus complaint location        |
| reporter_id | University ID of the reporter    |
| assigned_to | Assigned person                  |
| created_at  | Complaint creation date and time |

**Note:** Hibernate automatically creates or updates the entity tables when the backend starts because the project uses `spring.jpa.hibernate.ddl-auto=update`.

---

## 📂 9. Project Folder Structure

```text
CampusCure/
│
├── .github/
│
├── backend/
│   │
│   ├── pom.xml
│   │
│   └── src/
│       └── main/
│           │
│           ├── java/
│           │   └── com/
│           │       └── campuscure/
│           │           │
│           │           ├── CampusCureApplication.java
│           │           │
│           │           ├── config/
│           │           │   └── AppConfig.java
│           │           │
│           │           ├── controller/
│           │           │   ├── AuthController.java
│           │           │   ├── ComplaintController.java
│           │           │   └── HealthController.java
│           │           │
│           │           ├── model/
│           │           │   ├── AppUser.java
│           │           │   └── Complaint.java
│           │           │
│           │           └── repo/
│           │               ├── UserRepository.java
│           │               └── ComplaintRepository.java
│           │
│           └── resources/
│               └── application.properties
│
├── database/
│   ├── README.md
│   └── schema.sql
│
├── frontend/
│   │
│   ├── index.html
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.js
│   │
│   └── src/
│       ├── App.jsx
│       ├── main.jsx
│       └── style.css
│
├── .gitignore
└── README.md
```

---

## 🔌 10. Backend REST API Endpoints

Base URL:

`http://localhost:8080/api`

### Authentication APIs

| Method | Endpoint           | Purpose                           |
| ------ | ------------------ | --------------------------------- |
| POST   | `/auth/register`   | Register a new user               |
| POST   | `/auth/login`      | Verify University ID and password |
| POST   | `/auth/verify-otp` | Verify login OTP                  |

### Complaint APIs

| Method | Endpoint                  | Purpose                       |
| ------ | ------------------------- | ----------------------------- |
| GET    | `/complaints`             | Retrieve all complaints       |
| POST   | `/complaints`             | Create a new complaint        |
| PATCH  | `/complaints/{id}/status` | Update complaint status       |
| PATCH  | `/complaints/{id}/assign` | Assign a complaint            |
| GET    | `/complaints/summary`     | Retrieve complaint statistics |

---

## ⚙️ 11. Installation and Setup

### Prerequisites

Install the following software:

* Java Development Kit 21.
* Apache Maven.
* Node.js and npm.
* MySQL Server 8.
* Visual Studio Code.
* MySQL Workbench (recommended).

### Step 1: Clone the Repository

```bash
git clone https://github.com/Jhanasri-thota/DBS-DS_PROJECT.git
```

Navigate to the project folder:

```bash
cd DBS-DS_PROJECT/Hackathon_CampusCure
```

### Step 2: Configure MySQL

Open MySQL Workbench and execute:

```sql
CREATE DATABASE campuscure;
```

### Step 3: Configure Backend Database Connection

Open:

`backend/src/main/resources/application.properties`

Configure your MySQL credentials:

```properties
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/campuscure?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.

### Step 4: Run the Backend

Open a terminal:

```bash
cd backend
```

Run:

```bash
mvn spring-boot:run
```

Backend server:

`http://localhost:8080`

### Step 5: Run the Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend URL:

`http://localhost:5173`

### Step 6: Open the Application

Open your browser and visit:

`http://localhost:5173`

Register an account and explore CampusCure.

---

## 🔑 12. OTP Verification

CampusCure implements a six-digit random OTP verification step after successful University ID and password verification.

Authentication workflow:

1. User enters University ID and password.
2. Backend verifies the credentials.
3. Backend generates a random six-digit OTP.
4. User enters the generated OTP.
5. Backend validates the OTP.
6. Successful verification completes the login process.

**Demonstration limitation:** The current project returns the generated OTP through the API and displays it in the frontend. It does not send OTPs through SMS or email. OTP storage is in-memory, and the returned UUID is a demo token rather than a JWT. This implementation is intended for local demonstration, not production deployment.

---

## 🚀 13. Future Enhancements

The project can be extended with:

* Email and SMS OTP integration.
* JWT-based authentication.
* Role-based authorization.
* Admin-specific dashboard.
* Staff-specific complaint management.
* Complaint history and audit logs.
* File and image attachments.
* Email notifications.
* Complaint feedback and ratings.
* Advanced analytics and graphical reports.
* Search, filtering and pagination.
* Deployment to a cloud platform.

---

## 👩‍💻 14. Project Team

This project was developed collaboratively by the following team members.

| S.No. | Team Member | University ID |
| ----- | ----------- | ------------- |
| 1     | Jhanasri    | 2520090121    |
| 2     | Alekhya     | 2520090126    |
| 3     | Keerthana   | 2520090132    |

### Team Responsibilities

The team collaboratively worked on project planning, application development, frontend design, backend implementation, database integration, testing and documentation.

---

## 🎓 15. Project Outcome

CampusCure demonstrates how a centralized full-stack web application can simplify campus complaint registration and tracking.

By integrating React, Spring Boot and MySQL, the system provides a practical foundation for improving communication and visibility around campus maintenance issues.

The project demonstrates the application of:

* Frontend development.
* REST API development.
* Java backend programming.
* Database management.
* Authentication and password hashing.
* Full-stack application integration.
* Git and GitHub collaboration.

---

## ❤️ 16. Acknowledgement

We sincerely thank our faculty, mentors and institution for providing the opportunity and support to develop this project.

We hope CampusCure contributes towards creating a more organized, responsive and digitally connected campus environment.

<p align="center">
  <b>🏫 CampusCure – Your Campus. Your Voice. Our Responsibility.</b>
</p>
