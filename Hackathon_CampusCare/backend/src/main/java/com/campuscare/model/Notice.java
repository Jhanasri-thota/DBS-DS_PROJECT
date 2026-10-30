package com.campuscare.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="notifications")
public class Notice {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private User user;
 @Column(nullable=false) private String message;
 private boolean readFlag=false; private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;}
 public String getMessage(){return message;} public void setMessage(String v){message=v;}
 public boolean isReadFlag(){return readFlag;} public void setReadFlag(boolean v){readFlag=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
