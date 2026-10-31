package com.campuscure.model;
import jakarta.persistence.*;
@Entity @Table(name="app_users") public class AppUser {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false,unique=true) private String universityId;
@Column(nullable=false) private String fullName;
@Column(nullable=false,unique=true) private String email;
@Column(nullable=false) private String passwordHash;
@Column(nullable=false) private String role;
public Long getId(){return id;} public String getUniversityId(){return universityId;} public void setUniversityId(String v){universityId=v;}
public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
public String getEmail(){return email;} public void setEmail(String v){email=v;}
public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
public String getRole(){return role;} public void setRole(String v){role=v;}
}