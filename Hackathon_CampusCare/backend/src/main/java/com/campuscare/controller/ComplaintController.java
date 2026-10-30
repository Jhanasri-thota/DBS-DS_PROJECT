package com.campuscare.controller;
import com.campuscare.model.*;
import com.campuscare.repo.*;
import com.campuscare.security.AuthSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/complaints")
public class ComplaintController {
 private final ComplaintRepository complaints;private final UserRepository users;private final AuthSupport auth;
 public ComplaintController(ComplaintRepository c,UserRepository u,AuthSupport a){complaints=c;users=u;auth=a;}
 public record Create(@NotBlank String title,@NotBlank @Size(min=5) String description,String category,String location,String building,String priority){}
 public record Status(String status,String resolutionNote){}
 private Map<String,Object> view(Complaint c){Map<String,Object> m=new LinkedHashMap<>();m.put("id",c.getId());m.put("title",c.getTitle());m.put("description",c.getDescription());m.put("category",c.getCategory());m.put("location",c.getLocation());m.put("building",c.getBuilding());m.put("priority",c.getPriority());m.put("status",c.getStatus());m.put("createdAt",c.getCreatedAt());m.put("submittedBy",c.getSubmittedBy().getFullName());m.put("submittedById",c.getSubmittedBy().getId());m.put("assignedStaff",c.getAssignedStaff()==null?"Unassigned":c.getAssignedStaff().getFullName());m.put("assignedStaffId",c.getAssignedStaff()==null?null:c.getAssignedStaff().getId());m.put("resolutionNote",c.getResolutionNote());return m;}
 @PostMapping public Object create(@Valid @RequestBody Create x,HttpServletRequest r){User u=auth.current(r);auth.require(u,"STUDENT","FACULTY");Complaint c=new Complaint();c.setTitle(x.title());c.setDescription(x.description());c.setCategory(x.category());c.setLocation(x.location());c.setBuilding(x.building());c.setPriority(x.priority()==null?"MEDIUM":x.priority().toUpperCase());c.setSubmittedBy(u);return view(complaints.save(c));}
 @GetMapping public Object list(HttpServletRequest r){User u=auth.current(r);if(u.getRole().equals("ADMIN"))return complaints.findAllByOrderByCreatedAtDesc().stream().map(this::view).toList();if(u.getRole().equals("STAFF"))return complaints.findByAssignedStaffIdOrderByCreatedAtDesc(u.getId()).stream().map(this::view).toList();return complaints.findBySubmittedByIdOrderByCreatedAtDesc(u.getId()).stream().map(this::view).toList();}
 @PutMapping("/{id}/assign/{staffId}") public Object assign(@PathVariable Long id,@PathVariable Long staffId,HttpServletRequest r){auth.require(auth.current(r),"ADMIN");Complaint c=complaints.findById(id).orElseThrow();User s=users.findById(staffId).orElseThrow();if(!s.getRole().equals("STAFF")||!s.isApproved()||!s.isEnabled())throw new IllegalArgumentException("Select an active approved staff account");c.setAssignedStaff(s);c.setStatus("ASSIGNED");c.touch();return view(complaints.save(c));}
 @PutMapping("/{id}/status") public Object status(@PathVariable Long id,@Valid @RequestBody Status x,HttpServletRequest r){User u=auth.current(r);Complaint c=complaints.findById(id).orElseThrow();if(!u.getRole().equals("ADMIN")&&(c.getAssignedStaff()==null||!c.getAssignedStaff().getId().equals(u.getId())))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN);Set<String> allowed=Set.of("UNDER_REVIEW","ASSIGNED","IN_PROGRESS","RESOLVED","CLOSED","REJECTED");if(!allowed.contains(x.status()))throw new IllegalArgumentException("Invalid status");c.setStatus(x.status());c.setResolutionNote(x.resolutionNote());c.touch();return view(complaints.save(c));}
}
