package com.campuscare.controller;
import com.campuscare.model.User;
import com.campuscare.repo.UserRepository;
import com.campuscare.security.AuthSupport;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/users")
public class UserController {
 private final UserRepository users; private final AuthSupport auth;
 public UserController(UserRepository users,AuthSupport auth){this.users=users;this.auth=auth;}
 private Map<String,Object> view(User u){Map<String,Object> m=new LinkedHashMap<>();m.put("id",u.getId());m.put("name",u.getFullName());m.put("email",u.getEmail());m.put("role",u.getRole());m.put("department",u.getDepartment());m.put("universityId",u.getUniversityId());m.put("approved",u.isApproved());m.put("enabled",u.isEnabled());return m;}
 @GetMapping("/me") public Object me(HttpServletRequest r){return view(auth.current(r));}
 @GetMapping public Object all(HttpServletRequest r){auth.require(auth.current(r),"ADMIN");return users.findAll().stream().map(this::view).toList();}
 @GetMapping("/pending") public Object pending(HttpServletRequest r){auth.require(auth.current(r),"ADMIN");return users.findAll().stream().filter(u->!u.isApproved()&&Set.of("FACULTY","STAFF").contains(u.getRole())).map(this::view).toList();}
 @PutMapping("/{id}/approve") public Object approve(@PathVariable Long id,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");User u=users.findById(id).orElseThrow();if(!Set.of("FACULTY","STAFF").contains(u.getRole()))throw new IllegalArgumentException("Only Faculty/Staff accounts can be approved here");u.setApproved(true);u.setEnabled(true);return view(users.save(u));}
 @PutMapping("/{id}/reject") public Object reject(@PathVariable Long id,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");User u=users.findById(id).orElseThrow();u.setApproved(false);u.setEnabled(false);return view(users.save(u));}
 @PutMapping("/{id}/toggle") public Object toggle(@PathVariable Long id,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");User u=users.findById(id).orElseThrow();u.setEnabled(!u.isEnabled());return view(users.save(u));}
}
