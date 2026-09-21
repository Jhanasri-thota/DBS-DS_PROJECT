package com.votesecure.onlinevoting.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="voter_id", length=50) private String voterId;
    @Column(length=60) private String action;
    @Column(length=500) private String details;
    @Column(name="created_at") private LocalDateTime createdAt;
    @PrePersist void init(){createdAt=LocalDateTime.now();}
    public AuditLog(){}
    public AuditLog(String voterId,String action,String details){this.voterId=voterId;this.action=action;this.details=details;}
    public Long getId(){return id;} public String getVoterId(){return voterId;} public String getAction(){return action;} public String getDetails(){return details;} public LocalDateTime getCreatedAt(){return createdAt;}
}
