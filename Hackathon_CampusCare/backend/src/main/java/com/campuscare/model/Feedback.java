package com.campuscare.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="feedback")
public class Feedback {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Complaint complaint;
 @ManyToOne(optional=false) private User user;
 private int rating; @Column(length=1000) private String comment;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public Complaint getComplaint(){return complaint;} public void setComplaint(Complaint v){complaint=v;}
 public User getUser(){return user;} public void setUser(User v){user=v;} public int getRating(){return rating;} public void setRating(int v){rating=v;}
 public String getComment(){return comment;} public void setComment(String v){comment=v;}
}
