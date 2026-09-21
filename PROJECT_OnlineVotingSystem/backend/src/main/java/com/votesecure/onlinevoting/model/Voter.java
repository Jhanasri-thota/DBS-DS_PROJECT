package com.votesecure.onlinevoting.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "voters")
public class Voter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name="voter_id", unique=true) private String voterId;
    @Column(name="full_name", nullable=false, length=120) private String fullName;
    @Column(name="date_of_birth") private LocalDate dateOfBirth;
    @Column(length=20) private String gender;
    @Column(unique=true, length=150) private String email;
    @Column(length=20) private String phone;
    @Column(name="identity_ref", length=100) private String identityRef;
    private String password;
    @Column(name="account_status", nullable=false, length=20) private String accountStatus = "PENDING";
    @Column(name="has_voted") private boolean hasVoted;
    @Column(name="otp_verified") private boolean otpVerified;
    @Column(name="created_at") private LocalDateTime createdAt;

    @PrePersist void prePersist(){ if(createdAt==null) createdAt=LocalDateTime.now(); }
    public Long getId(){return id;} public String getVoterId(){return voterId;} public void setVoterId(String v){voterId=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public LocalDate getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;}
    public String getGender(){return gender;} public void setGender(String v){gender=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getIdentityRef(){return identityRef;} public void setIdentityRef(String v){identityRef=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;} public String getAccountStatus(){return accountStatus;} public void setAccountStatus(String v){accountStatus=v;}
    public boolean isHasVoted(){return hasVoted;} public void setHasVoted(boolean v){hasVoted=v;} public boolean isOtpVerified(){return otpVerified;} public void setOtpVerified(boolean v){otpVerified=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
