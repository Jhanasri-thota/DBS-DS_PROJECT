package com.campuscare.controller;
import com.campuscare.model.User;
import com.campuscare.repo.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AuthController(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 public record Register(@NotBlank String fullName,@NotBlank @Email String email,@NotBlank @Size(min=8) String password,String phone,String universityId,String department,String role){}
 public record Login(@NotBlank @Email String email,@NotBlank String password){}
 @PostMapping("/setup-admin") public ResponseEntity<?> setupAdmin(@Valid @RequestBody Register r){
  if(users.findByRole("ADMIN").size()>0) return ResponseEntity.status(409).body(Map.of("message","An administrator is already configured."));
  User u=new User(); u.setFullName(r.fullName().trim());u.setEmail(r.email().trim().toLowerCase());u.setPasswordHash(encoder.encode(r.password()));u.setPhone(r.phone());u.setUniversityId(r.universityId());u.setDepartment(r.department());u.setRole("ADMIN");u.setApproved(true);u.setEnabled(true);users.save(u);
  return ResponseEntity.ok(Map.of("message","Administrator account created. Please sign in."));
 }
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody Register r){
  String email=r.email().trim().toLowerCase();
  if(users.existsByEmail(email)) return ResponseEntity.badRequest().body(Map.of("message","Email already registered"));
  String role=r.role()==null?"STUDENT":r.role().trim().toUpperCase();
  if(!Set.of("STUDENT","FACULTY","STAFF").contains(role)) return ResponseEntity.badRequest().body(Map.of("message","Choose Student, Faculty or Staff"));
  User u=new User();u.setFullName(r.fullName().trim());u.setEmail(email);u.setPasswordHash(encoder.encode(r.password()));u.setPhone(r.phone());u.setUniversityId(r.universityId());u.setDepartment(r.department());u.setRole(role);u.setApproved(role.equals("STUDENT"));u.setEnabled(true);users.save(u);
  return ResponseEntity.ok(Map.of("message",u.isApproved()?"Account created. You can sign in.":"Registration submitted. Wait for administrator approval."));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody Login r){
  User u=users.findByEmail(r.email().trim().toLowerCase()).orElse(null);
  if(u==null||!encoder.matches(r.password(),u.getPasswordHash())) return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));
  if(!u.isEnabled()||!u.isApproved()) return ResponseEntity.status(403).body(Map.of("message","Your account is waiting for administrator approval or is disabled."));
  String token=u.getId()+":"+u.getEmail();
  return ResponseEntity.ok(Map.of("token",token,"user",Map.of("id",u.getId(),"name",u.getFullName(),"email",u.getEmail(),"role",u.getRole())));
 }
}
