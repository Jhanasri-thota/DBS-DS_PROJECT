package com.campuscare.controller;
import com.campuscare.model.Department;import com.campuscare.repo.DepartmentRepository;import com.campuscare.security.AuthSupport;
import jakarta.servlet.http.HttpServletRequest;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/departments")
public class DepartmentController {
 private final DepartmentRepository repo;private final AuthSupport auth;
 public DepartmentController(DepartmentRepository r,AuthSupport a){repo=r;auth=a;}
 @GetMapping public Object list(){return repo.findAll();}
 @PostMapping public Object create(@RequestBody Department d,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");return repo.save(d);}
 @DeleteMapping("/{id}") public Object delete(@PathVariable Long id,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");repo.deleteById(id);return Map.of("message","Department deleted");}
}
