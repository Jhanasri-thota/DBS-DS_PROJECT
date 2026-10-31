package com.campuscure.model;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="complaints") public class Complaint {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false) private String title;
@Column(nullable=false,length=3000) private String description;
private String category;private String priority;private String status="Submitted";private String location;private String reporterId;private String assignedTo;
private LocalDateTime createdAt=LocalDateTime.now();
public Long getId(){return id;}public String getTitle(){return title;}public void setTitle(String v){title=v;}
public String getDescription(){return description;}public void setDescription(String v){description=v;}
public String getCategory(){return category;}public void setCategory(String v){category=v;}
public String getPriority(){return priority;}public void setPriority(String v){priority=v;}
public String getStatus(){return status;}public void setStatus(String v){status=v;}
public String getLocation(){return location;}public void setLocation(String v){location=v;}
public String getReporterId(){return reporterId;}public void setReporterId(String v){reporterId=v;}
public String getAssignedTo(){return assignedTo;}public void setAssignedTo(String v){assignedTo=v;}
public LocalDateTime getCreatedAt(){return createdAt;}
}