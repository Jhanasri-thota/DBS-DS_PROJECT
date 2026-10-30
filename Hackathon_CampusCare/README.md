# 🏫 CampusCare – AI-Powered Campus Complaint & Service Management System

<p align="center">
  <b>One Platform. Every Campus Concern. Better Campus Life.</b>
  <br><br>
  A Full-Stack Web Application developed for CODESPARK Hackathon 2026 – Problem Statement 20.
</p>

---

## 📌 1. Project Overview

**CampusCare** is an AI-powered Campus Complaint and Service Management System designed to make campus issue reporting, tracking and resolution easier and more efficient.

The platform provides a centralized digital environment where students and faculty can register complaints related to campus facilities and services. Administrators can manage users, assign complaints to staff members, monitor progress and analyze complaint statistics.

The system also includes an optional AI-supported service that recommends complaint categories, priorities and departments based on the complaint description.

Our goal is to improve communication, transparency and accountability in campus maintenance and service management.

## 🎯 2. Problem Statement

Traditional campus complaint management often depends on manual reporting, paperwork and direct communication. This can result in delayed responses, difficulty tracking complaints and a lack of transparency.

CampusCare addresses these challenges through a centralized web-based complaint management platform.

## 💡 3. Proposed Solution

CampusCare provides:

* Digital complaint registration.
* Role-based access for different users.
* Complaint categorization and priority recommendations.
* Department and staff assignment.
* Real-time complaint status tracking.
* Complaint history and resolution remarks.
* Feedback collection.
* Administrative analytics and CSV reports.
* SLA-based overdue complaint identification.

## ✨ 4. Key Features

### 👨‍🎓 Student Module

* Student registration and login.
* Submit campus-related complaints.
* Select complaint categories and priorities.
* Provide campus location details.
* Track complaint status.
* View complaint history.
* Submit feedback after resolution.

### 👩‍🏫 Faculty Module

* Faculty registration.
* Administrator approval workflow.
* Secure login after approval.
* Complaint registration and tracking.

### 🧑‍🔧 Staff Module

* Administrator-provisioned staff accounts.
* View assigned complaints.
* Update complaint status.
* Add resolution remarks.
* Maintain complaint progress.

### 👨‍💼 Administrator Module

* Manage registered users.
* Approve faculty accounts.
* Provision staff accounts.
* Assign complaints to staff and departments.
* Monitor complaint progress.
* View dashboard analytics.
* Identify overdue complaints.
* Export complaint reports in CSV format.

### 🤖 AI-Supported Complaint Recommendations

The optional Python FastAPI service provides rule-based recommendations for:

* Complaint category.
* Complaint priority.
* Responsible department.

If the AI service is unavailable, the Java backend includes a deterministic fallback classification mechanism.

## 🛠️ 5. Technology Stack

| Layer                 | Technologies                      |
| --------------------- | --------------------------------- |
| Frontend              | React.js, JavaScript              |
| Build Tool            | Vite                              |
| UI Icons              | Lucide React                      |
| Charts                | Recharts                          |
| API Communication     | Axios                             |
| Backend               | Java 21, Spring Boot 3.4.5        |
| Backend Security      | Spring Security                   |
| Authentication        | JWT                               |
| Password Security     | BCrypt                            |
| ORM                   | Spring Data JPA, Hibernate        |
| Database              | MySQL 8                           |
| Supporting AI Service | Python, FastAPI                   |
| API Documentation     | Swagger / OpenAPI                 |
| Version Control       | Git, GitHub                       |
| Development Tools     | VS Code, MySQL Workbench, Postman |

## 🏗️ 6. System Architecture

```text
                 CAMPUSCARE
                      |
          +-----------+-----------+
          |                       |
       FRONTEND                BACKEND
          |                       |
     React + Vite           Java + Spring Boot
          |                       |
          |                  REST APIs
          |                       |
          |                JWT Authentication
          |                       |
          |                 Spring Data JPA
          |                       |
          |                  MySQL 8
          |
```
