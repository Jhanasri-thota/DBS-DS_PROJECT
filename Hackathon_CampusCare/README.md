# 🏫 CampusCare

### AI-Powered Campus Complaint & Service Management System

<p align="center">
  <b>One Platform. Every Campus Concern. Better Campus Life.</b>
  <br>
  A Smart Digital Solution for Efficient Campus Complaint Management
</p>

---

## 📌 1. Project Overview

**CampusCare** is a full-stack, AI-supported Campus Complaint and Service Management System developed to simplify communication between students, faculty, administrators and campus maintenance staff.

In traditional campus environments, reporting and resolving issues can be time-consuming due to manual complaint registration, delayed communication and the lack of proper tracking.

CampusCare addresses these challenges by providing a centralized web application where users can submit complaints, track their progress and receive updates until resolution.

The platform also provides administrators with tools to manage users, assign complaints, monitor service activities and analyze complaint statistics.

An optional AI-support service recommends complaint categories, priorities and responsible departments based on complaint descriptions.

### 🎯 Our Mission

To make campus complaint management more accessible, transparent, organized and efficient through technology.

---

## 🚨 2. Problem Statement

Traditional campus complaint management systems often face challenges such as:

* Manual complaint registration.
* Delays in resolving campus issues.
* Difficulty tracking complaint status.
* Lack of communication between users and maintenance teams.
* Improper complaint assignment.
* Limited administrative monitoring.
* Lack of centralized complaint records.

### 💡 Our Solution

CampusCare introduces a centralized digital platform that enables users to report issues, allows administrators to assign responsibilities and helps staff manage complaints efficiently.

---

## 🎯 3. Project Objectives

* Develop a centralized campus complaint management platform.
* Simplify complaint registration and tracking.
* Provide role-based access for different campus users.
* Improve coordination between administrators and staff.
* Support complaint categorization and priority recommendations.
* Maintain complaint history and resolution details.
* Provide administrative dashboards and reports.
* Improve transparency and accountability in campus services.

---

## ✨ 4. Key Features

### 👨‍🎓 Student Module

* Student registration and login.
* Submit campus-related complaints.
* Select complaint categories.
* Provide building, floor and room details.
* View submitted complaints.
* Track complaint status.
* View complaint details and history.
* Submit feedback after complaint resolution.

### 👩‍🏫 Faculty Module

* Faculty registration.
* Administrator approval workflow.
* Secure login after approval.
* Register campus-related complaints.
* Track complaint progress.

### 🧑‍🔧 Staff Module

* Staff account management.
* View assigned complaints.
* Update complaint status.
* Add resolution remarks.
* Manage assigned complaint progress.

### 👨‍💼 Administrator Module

* View registered users.
* Approve faculty accounts.
* Manage user access.
* Assign complaints to staff.
* Monitor complaint progress.
* View complaint analytics.
* Identify overdue complaints.
* Export complaint reports in CSV format.

### 🤖 AI-Supported Complaint Recommendations

The optional AI service analyzes complaint descriptions and recommends:

* Complaint category.
* Complaint priority.
* Responsible department.

The recommendation service uses a transparent, rule-based approach.

If the AI service is unavailable, the Java backend provides a deterministic fallback classification mechanism.

### 📊 Dashboard & Reporting

* Complaint statistics.
* Complaint status monitoring.
* Overdue complaint identification.
* Department-based complaint information.
* CSV report export.

---

## 🛠️ 5. Technology Stack

| Component             | Technology         |
| --------------------- | ------------------ |
| Frontend              | React.js           |
| Build Tool            | Vite               |
| Programming Language  | JavaScript         |
| UI Icons              | Lucide React       |
| Charts                | Recharts           |
| API Communication     | Axios              |
| Backend               | Java 21            |
| Backend Framework     | Spring Boot 3.4.5  |
| Security              | Spring Security    |
| Authentication        | JWT                |
| Password Encryption   | BCrypt             |
| ORM                   | Spring Data JPA    |
| Database              | MySQL 8            |
| Supporting AI Service | Python + FastAPI   |
| API Documentation     | Swagger / OpenAPI  |
| Version Control       | Git & GitHub       |
| Code Editor           | Visual Studio Code |
| API Testing           | Postman            |
| Database Management   | MySQL Workbench    |

**Architecture Note:** Java Spring Boot is the primary application backend. Python FastAPI is an optional supporting service for complaint recommendations.

---

## 🏗️ 6. System Architecture

```text
                  CAMPUSCARE
                       |
             +---------+---------+
             |                   |
          FRONTEND             BACKEND
             |                   |
         React.js            Java 21
           Vite             Spring Boot
             |                   |
             |              REST APIs
             |                   |
             |           Spring Security
             |                   |
             |             JWT Auth
             |                   |
             |             Spring Data JPA
             |                   |
             |                MySQL
             |
             |
       Optional AI Service
             |
          FastAPI
             |
      Complaint Description
             |
     Category Recommendation
     Priority Recommendation
     Department Recommendation
```

---

## 🔄 7. Application Workflow

```text
             USER REGISTRATION
                     |
                 USER LOGIN
                     |
             SUBMIT COMPLAINT
                     |
          AI-BASED RECOMMENDATIONS
                     |
             COMPLAINT CREATED
                     |
              ADMIN REVIEWS
                     |
             STAFF ASSIGNMENT
                     |
             STAFF UPDATES
                     |
            COMPLAINT RESOLUTION
                     |
               USER FEEDBACK
                     |
              COMPLAINT CLOSED
```

---

## 📂 8. Project Folder Structure

```text
Hackathon_CampusCare/
│
├── backend/
│   │
│   ├── src/main/java/com/campuscare/
│   │   │
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── AdminController.java
│   │   │   ├── ComplaintController.java
│   │   │   ├── MetaController.java
│   │   │   └── GlobalExceptionHandler.java
│   │   │
│   │   ├── model/
│   │   │   ├── User.java
│
```
