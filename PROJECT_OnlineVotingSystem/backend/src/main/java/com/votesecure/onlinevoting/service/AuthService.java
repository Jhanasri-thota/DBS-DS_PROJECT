package com.votesecure.onlinevoting.service;

import com.votesecure.onlinevoting.model.AuditLog;
import com.votesecure.onlinevoting.model.Voter;
import com.votesecure.onlinevoting.repository.AuditLogRepository;
import com.votesecure.onlinevoting.repository.VoterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class AuthService {
    private final VoterRepository voters;
    private final AuditLogRepository audit;
    private final PasswordEncoder encoder;
    public AuthService(VoterRepository voters, AuditLogRepository audit, PasswordEncoder encoder){this.voters=voters;this.audit=audit;this.encoder=encoder;}

    public Map<String,Object> login(String voterId,String password){
        Voter v=voters.findByVoterId(voterId.trim()).orElse(null);
        if(v==null || v.getPassword()==null || !encoder.matches(password,v.getPassword()) || !"ACTIVE".equals(v.getAccountStatus())) throw new IllegalArgumentException("Invalid credentials. Register an account first.");
        audit.save(new AuditLog(v.getVoterId(),"LOGIN","Successful voter login"));
        return Map.of("accessToken",v.getVoterId(),"voter",Map.of("voterId",v.getVoterId(),"fullName",v.getFullName(),"email",v.getEmail()==null?"":v.getEmail(),"phone",v.getPhone()==null?"":v.getPhone(),"hasVoted",v.isHasVoted(),"otpVerified",v.isOtpVerified()));
    }

    public Voter register(String voterId,String fullName,String dob,String gender,String email,String phone,String identityRef,String password){
        if(voterId==null||voterId.isBlank()||fullName==null||fullName.isBlank()||email==null||email.isBlank()||password==null||password.length()<8) throw new IllegalArgumentException("Username, name, email and an 8+ character password are required.");
        if(voters.findByVoterId(voterId.trim()).filter(v->v.getPassword()!=null).isPresent()) throw new IllegalStateException("That Voter ID is already registered.");
        Voter v=voters.findByEmail(email.trim()).orElseGet(Voter::new);
        v.setVoterId(voterId.trim()); v.setFullName(fullName.trim()); v.setEmail(email.trim()); v.setPhone(phone==null?"":phone.trim()); v.setGender(gender); v.setIdentityRef(identityRef);
        if(dob!=null&&!dob.isBlank()) v.setDateOfBirth(LocalDate.parse(dob));
        v.setPassword(encoder.encode(password)); v.setAccountStatus("ACTIVE"); v.setOtpVerified(true); v.setHasVoted(false);
        Voter saved=voters.save(v); audit.save(new AuditLog(saved.getVoterId(),"REGISTER","Voter account created by user")); return saved;
    }
}
