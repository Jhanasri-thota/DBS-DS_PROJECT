package com.campuscare.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String fullName;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String passwordHash;
 private String phone; private String universityId; private String department;
 @Column(nullable=false) private String role;
 private boolean approved; private boolean enabled=true;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getUniversityId(){return universityId;} public void setUniversityId(String v){universityId=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;} public String getRole(){return role;} public void setRole(String v){role=v;}
 public boolean isApproved(){return approved;} public void setApproved(boolean v){approved=v;} public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
