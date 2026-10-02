package com.votehub.entity;
import jakarta.persistence.*; import java.time.*;
@Entity @Table(name="audit_logs") public class AuditLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @ManyToOne @JoinColumn(name="actor_user_id") public User actor; @Column(nullable=false) public String action; @Column(columnDefinition="TEXT") public String details; @Column(name="created_at",nullable=false) public LocalDateTime createdAt=LocalDateTime.now(); }
