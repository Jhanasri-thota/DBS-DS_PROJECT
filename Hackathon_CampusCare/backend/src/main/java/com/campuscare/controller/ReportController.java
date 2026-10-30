package com.campuscare.controller;
import com.campuscare.repo.*;import com.campuscare.security.AuthSupport;import jakarta.servlet.http.HttpServletRequest;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/reports")
public class ReportController {
 private final UserRepository users;private final ComplaintRepository complaints;private final AuthSupport auth;
 public ReportController(UserRepository u,ComplaintRepository c,AuthSupport a){users=u;complaints=c;auth=a;}
 @GetMapping("/summary") public Object summary(HttpServletRequest r){auth.require(auth.current(r),"ADMIN");var all=complaints.findAll();Map<String,Object> m=new LinkedHashMap<>();m.put("totalUsers",users.count());m.put("students",users.findByRole("STUDENT").size());m.put("faculty",users.findByRole("FACULTY").size());m.put("staff",users.findByRole("STAFF").size());m.put("totalComplaints",all.size());m.put("pendingComplaints",all.stream().filter(c->!Set.of("RESOLVED","CLOSED","REJECTED").contains(c.getStatus())).count());m.put("resolvedComplaints",all.stream().filter(c->c.getStatus().equals("RESOLVED")||c.getStatus().equals("CLOSED")).count());return m;}
}
