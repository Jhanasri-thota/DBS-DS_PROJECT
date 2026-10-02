package com.votehub.entity;
import jakarta.persistence.*;
@Entity @Table(name="candidates") public class Candidate { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @ManyToOne(optional=false) @JoinColumn(name="election_id") public Election election; @Column(nullable=false) public String name; @Column(nullable=false) public String position; @Column(columnDefinition="TEXT") public String description; public String photoUrl; }
