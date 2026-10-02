# VoteHub – Online Voting System

## 📌 Project Overview

VoteHub is a full-stack, secure and privacy-aware Online Voting System developed as an academic project. The system provides a digital platform where eligible voters can register, verify their identity using OTP, log in securely, view available elections, verify their eligibility, cast a vote and view voting-related information.

The system also provides an Admin Portal through which administrators can manage elections, candidates, voters, election status, results and audit information.

The project is implemented using React and Vite for the frontend, Java 21 and Spring Boot for the backend, MySQL for database management and REST APIs for communication between application components.

In addition to the main backend, the project demonstrates a CO6 Microservices Architecture by separating authentication, election management and voting responsibilities into independent services.

---

## 🎯 Project Objectives

The main objectives of VoteHub are:

- To provide a simple and user-friendly online voting platform.
- To implement secure voter registration and authentication.
- To verify users using OTP-based verification.
- To protect user passwords using BCrypt hashing.
- To securely handle sensitive Aadhaar-related information.
- To verify voter eligibility before allowing voting.
- To ensure that a voter can vote only once in an election.
- To prevent duplicate votes using backend and database-level validation.
- To maintain a privacy-aware separation between voter identity and ballot information.
- To provide election and candidate management facilities.
- To provide aggregate election results.
- To maintain audit information for important system activities.
- To demonstrate microservices-based backend development.

---

## 👥 User Roles

### Voter

A voter can:

- Register an account.
- Verify the account using OTP.
- Log in using Voter ID and password.
- View the voter dashboard.
- View upcoming and active elections.
- Check election details.
- Verify voting eligibility.
- Cast a vote.
- View voting history.
- Receive notifications.
- Manage profile and security settings.
- Log out securely.

### Administrator

An administrator can:

- Register using Employee ID.
- Verify registration using OTP.
- Log in using administrator credentials.
- Create elections.
- Edit elections.
- Start and close elections.
- Manage candidates.
- Manage voters.
- Monitor election participation.
- View aggregate results.
- Publish results.
- View audit logs.
- Manage system settings.

---

## 🔐 Authentication and Registration

### Voter Registration

The voter registration form contains:

- Voter ID
- Full Name
- Date of Birth
- Aadhaar Number
- Email
- Mobile Number
- Password
- Confirm Password
- Terms and Conditions

The system validates the registration details before creating the account.

The system checks:

- Voter ID uniqueness
- Email uniqueness
- Minimum age requirement
- Password confirmation
- OTP verification

### Admin Registration

Administrator registration contains:

- Employee ID
- Full Name
- Official Email
- Mobile Number
- Password
- Confirm Password
- OTP verification

No pre-created administrator account is required.

---

## 📱 OTP Verification

VoteHub implements a demo OTP verification mechanism.

The OTP system provides:

- 6-digit OTP generation
- SecureRandom-based generation
- 5-minute OTP expiry
- Maximum 3 verification attempts
- Resend OTP functionality
- Previous OTP invalidation after resend
- Secure OTP storage using hashing

The OTP mechanism is implemented for academic demonstration.

> **Important:** The project uses Aadhaar-style OTP verification only as a simulation. It is NOT connected to UIDAI and does not perform real Aadhaar authentication.

---

## 🔒 Password and Data Security

The application implements several security mechanisms:

- BCrypt password hashing
- OTP verification
- OTP expiration
- OTP attempt limits
- Authentication checks
- Authorization checks
- Sensitive data hashing/HMAC
- Database constraints
- Duplicate vote prevention
- Audit logging
- Privacy-aware ballot storage

Passwords are never intended to be stored as plain text.

---

## 🗳️ Voting System

The voting process follows multiple validation stages.

```text
User Login
    ↓
Verify Authentication
    ↓
Check Election Status
    ↓
Check Voter Eligibility
    ↓
Check Voting Authorization
    ↓
Check Previous Vote
    ↓
Select Candidate
    ↓
Confirm Vote
    ↓
Create Ballot
    ↓
Record Vote
    ↓
Mark Authorization as Used
    ↓
Create Audit Event
    ↓
Vote Successfully Recorded
