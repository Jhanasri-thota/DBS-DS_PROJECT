package com.campuscare.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="complaints")
public class Complaint {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false,length=3000) private String description;
 private String category; private String location; private String building; private String priority="MEDIUM";
 private String status="SUBMITTED"; private String resolutionNote;
 @ManyToOne(optional=false) private User submittedBy;
 @ManyToOne private User assignedStaff;
 private LocalDateTime createdAt=LocalDateTime.now(); private LocalDateTime updatedAt=LocalDateTime.now();
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public String getBuilding(){return building;} public void setBuilding(String v){building=v;}
 public String getPriority(){return priority;} public void setPriority(String v){priority=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public String getResolutionNote(){return resolutionNote;} public void setResolutionNote(String v){resolutionNote=v;}
 public User getSubmittedBy(){return submittedBy;} public void setSubmittedBy(User v){submittedBy=v;}
 public User getAssignedStaff(){return assignedStaff;} public void setAssignedStaff(User v){assignedStaff=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;} public void touch(){updatedAt=LocalDateTime.now();}
}
