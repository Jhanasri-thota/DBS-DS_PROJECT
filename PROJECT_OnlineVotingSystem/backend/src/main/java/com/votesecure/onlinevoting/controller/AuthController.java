package com.votesecure.onlinevoting.controller;

import com.votesecure.onlinevoting.model.Voter;
import com.votesecure.onlinevoting.repository.VoterRepository;
import com.votesecure.onlinevoting.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth; private final VoterRepository voters;
    public AuthController(AuthService auth,VoterRepository voters){this.auth=auth;this.voters=voters;}
    record Login(String voterId,String password){}
    record Otp(String voterId,String otp){}
    record Register(String voterId,String fullName,String dob,String gender,String email,String phone,String identityRef,String password,String otp){}

    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Login r){
        try{return ResponseEntity.ok(auth.login(r.voterId(),r.password()));}
        catch(IllegalArgumentException e){return ResponseEntity.status(401).body(Map.of("message",e.getMessage()));}
    }
    @PostMapping("/register") public ResponseEntity<?> register(@RequestBody Register r){
        if(!"123456".equals(r.otp())) return ResponseEntity.badRequest().body(Map.of("message","Invalid demo OTP. Use 123456."));
        try{Voter v=auth.register(r.voterId(),r.fullName(),r.dob(),r.gender(),r.email(),r.phone(),r.identityRef(),r.password());return ResponseEntity.ok(Map.of("message","Registration successful","voterId",v.getVoterId(),"fullName",v.getFullName()));}
        catch(IllegalStateException e){return ResponseEntity.status(409).body(Map.of("message",e.getMessage()));}
        catch(Exception e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()==null?"Registration failed":e.getMessage()));}
    }
    @PostMapping("/verify-otp") public ResponseEntity<?> verifyOtp(@RequestBody Otp r){
        if(!"123456".equals(r.otp())) return ResponseEntity.badRequest().body(Map.of("message","Invalid OTP. Use 123456 for this academic demo."));
        Voter v=voters.findByVoterId(r.voterId()).orElse(null); if(v==null)return ResponseEntity.status(404).body(Map.of("message","Voter not found"));
        v.setOtpVerified(true);voters.save(v);return ResponseEntity.ok(Map.of("verified",true));
    }
}
