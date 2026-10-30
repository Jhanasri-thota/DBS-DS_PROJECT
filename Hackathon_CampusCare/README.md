# 🏫 CampusCare – Campus Complaint & Service Management System

<p align="center">
  <b>A Smart, Simple and Efficient Campus Complaint Management Platform</b>
  <br>
  Making campus services more accessible, transparent and organized.
</p>

---

## 📌 About the Project

**CampusCare** is a full-stack web application designed to simplify complaint management and improve communication between students, faculty, staff and campus administrators.

Students can submit complaints, track their progress and provide feedback. Faculty and staff accounts can be managed through administrator approval, while administrators can assign complaints, manage users and monitor campus service activities.

The main objective is to create a centralized platform that makes campus issue reporting faster, easier and more transparent.

## 🎯 Project Objectives

* Provide a centralized platform for campus complaints.
* Make complaint registration and tracking easier.
* Improve communication between students and campus staff.
* Enable administrators to manage users and complaints.
* Maintain organized complaint and feedback records.
* Improve transparency in campus service management.

## ✨ Key Features

### 👨‍🎓 Student

* Student registration and login.
* Submit campus complaints.
* View submitted complaints.
* Track complaint status.
* Provide feedback.

### 👩‍🏫 Faculty

* Faculty registration.
* Administrator approval before account access.
* Access the platform after approval.

### 🧑‍🔧 Staff

* Staff registration.
* Administrator approval.
* View assigned complaints.
* Update complaint progress and status.

### 👨‍💼 Administrator

* First administrator account setup.
* Manage registered users.
* Approve or reject faculty and staff accounts.
* Enable or disable user accounts.
* View and assign complaints to staff.
* Manage campus departments.
* View complaint statistics and reports.

## 🛠️ Technologies Used

| Component             | Technology                  |
| --------------------- | --------------------------- |
| Frontend              | React.js                    |
| Build Tool            | Vite                        |
| Programming Languages | JavaScript, Java            |
| Backend               | Spring Boot                 |
| Database              | MySQL 8                     |
| ORM                   | Spring Data JPA / Hibernate |
| Security              | BCrypt Password Hashing     |
| API                   | REST API                    |
| Development IDE       | Visual Studio Code          |
| API Testing           | Postman                     |
| Database Tool         | MySQL Workbench             |
| Version Control       | Git & GitHub                |

## 🏗️ System Architecture

```text
                 CAMPUSCARE
                      |
          +-----------+-----------+
          |                       |
      FRONTEND                 BACKEND
          |                       |
     React + Vite             Spring Boot
          |                       |
          +------ REST APIs ------+
                      |
                  MySQL DB
                      |
          +-----------+-----------+
          |           |           |
        Users     Complaints   Departments
                      |
                   Feedback
```

## 📂 Project Structure

```text
Hackathon_CampusCare/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/campuscare/
│   │       │   ├── config/
│   │       │   ├── controller/
│   │       │   ├── model/
│   │       │   ├── repo/
│   │       │   └── security/
│   │       │
│   │       └── resources/
│   │           └── application.properties
│   │
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── style.css
│   │
│   ├── package.json
│   └── vite.config.js
│
├── database/
│   └── schema.sql
│
├── start-backend.bat
├── start-frontend.bat
└── README.md
```

## ⚙️ Installation & Setup

### 1. Prerequisites

Install the following:

* Java 21
* Apache Maven
* Node.js and npm
* MySQL 8
* Visual Studio Code

### 2. Clone the Repository

```bash
git clone https://github.com/Jhanasri-thota/DBS-DS_PROJECT.git
```

Navigate to the project directory:

```bash
cd DBS-DS_PROJECT/Hackathon_CampusCare
```

### 3. Database Setup

1. Open MySQL Workbench.
2. Open `database/schema.sql`.
3. Execute the SQL script.
4. Open the backend configuration file:

```text
backend/src/main/resources/application.properties
```

Configure your MySQL username and password.

### 4. Start the Backend

Open a terminal:

```bash
cd backend
mvn clean spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

### 5. Start the Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the frontend URL displayed in the terminal. The default Vite URL is:

```text
http://localhost:5173
```

### 6. Administrator Setup

Open:

```text
http://localhost:5173/setup-admin
```

Create the first administrator account. After setup, log in and manage campus users and complaints.

## 🔄 Application Workflow

```text
          User Registration
                 |
          +------+------+
          |             |
        Student      Faculty/Staff
          |             |
     Auto Approval   Admin Approval
          |             |
          +------+------+
                 |
               Login
                 |
          Submit Complaint
                 |
          Admin Assigns Staff
                 |
          Staff Updates Status
                 |
          Complaint Resolution
                 |
             Feedback
```

## 🔮 Future Enhancements

* Email and SMS notifications.
* Complaint history and detailed tracking.
* Advanced analytics dashboard.
* Mobile-friendly application improvements.
* Complaint priority management.
* More detailed administrative reports.

## 👥 Team Members

This project is developed collaboratively by:

| S. No. | Team Member  | University ID |
| :----: | ------------ | :-----------: |
|    1   | **Jhanasri** |   2520090121  |
|    2   | **Sindhuja** |   2520080045  |
|    3   | **Alekhya**  |   2520090126  |

## 🎓 Project Information

| Detail         | Description                           |
| -------------- | ------------------------------------- |
| Project Name   | CampusCare                            |
| Project Type   | Full-Stack Web Application            |
| Domain         | Campus Complaint & Service Management |
| Frontend       | React + Vite                          |
| Backend        | Java + Spring Boot                    |
| Database       | MySQL                                 |
| Repository     | DBS-DS_PROJECT                        |
| Project Folder | Hackathon_CampusCare                  |

## ❤️ Our Vision

We aim to make campus issue management more accessible, transparent and efficient through technology.

**CampusCare – Your Campus, Your Voice, Our Responsibility!**

---

<p align="center">
  <b>Developed with ❤️ by Team CampusCare</b>
</p>
