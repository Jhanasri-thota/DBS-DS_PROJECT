package com.campuscure.controller;
import com.campuscure.model.AppUser;import com.campuscure.repo.UserRepository;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.http.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import java.security.SecureRandom;import java.util.*;import java.util.concurrent.ConcurrentHashMap;
@RestController @RequestMapping("/api/auth") public class AuthController{
private final UserRepository users;private final PasswordEncoder encoder;private final Map<String,String> pending=new ConcurrentHashMap<>();private final SecureRandom random=new SecureRandom();
public AuthController(UserRepository u,PasswordEncoder e){users=u;encoder=e;}
public record Register(@NotBlank String universityId,@NotBlank String fullName,@Email @NotBlank String email,@Size(min=8) String password,String role){}
public record Login(@NotBlank String universityId,@NotBlank String password){}
public record Verify(@NotBlank String universityId,@NotBlank String otp){}
@PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody Register r){
String id=r.universityId().trim();if(users.existsByUniversityId(id))return ResponseEntity.badRequest().body(Map.of("message","University ID already registered"));
if(users.existsByEmail(r.email().toLowerCase()))return ResponseEntity.badRequest().body(Map.of("message","Email already registered"));
String role=Set.of("ADMIN","FACULTY","STAFF","STUDENT").contains(r.role()==null?"STUDENT":r.role().toUpperCase())?(r.role()==null?"STUDENT":r.role().toUpperCase()):"STUDENT";
AppUser u=new AppUser();u.setUniversityId(id);u.setFullName(r.fullName());u.setEmail(r.email().toLowerCase());u.setPasswordHash(encoder.encode(r.password()));u.setRole(role);users.save(u);
return ResponseEntity.ok(Map.of("message","Account created. Sign in now.","role",role));}
@PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody Login l){
AppUser u=users.findByUniversityId(l.universityId().trim()).orElse(null);if(u==null||!encoder.matches(l.password(),u.getPasswordHash()))return ResponseEntity.status(401).body(Map.of("message","Invalid University ID or password"));
String code=String.format("%06d",random.nextInt(1000000));pending.put(u.getUniversityId(),code);
return ResponseEntity.ok(Map.of("message","Password verified. Enter generated OTP.","otp",code,"universityId",u.getUniversityId()));}
@PostMapping("/verify-otp") public ResponseEntity<?> verify(@Valid @RequestBody Verify v){
String code=pending.get(v.universityId());if(code==null||!code.equals(v.otp()))return ResponseEntity.status(401).body(Map.of("message","Invalid OTP. Sign in again."));
pending.remove(v.universityId());AppUser u=users.findByUniversityId(v.universityId()).orElseThrow();return ResponseEntity.ok(Map.of("token",UUID.randomUUID().toString(),"user",Map.of("id",u.getId(),"universityId",u.getUniversityId(),"fullName",u.getFullName(),"email",u.getEmail(),"role",u.getRole())));
}}