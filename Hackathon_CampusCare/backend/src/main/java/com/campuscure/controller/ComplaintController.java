package com.campuscure.controller;
import com.campuscure.model.Complaint;import com.campuscure.repo.ComplaintRepository;import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/complaints") public class ComplaintController{
private final ComplaintRepository repo;public ComplaintController(ComplaintRepository r){repo=r;}
public record NewComplaint(@NotBlank String title,@NotBlank String description,String category,String priority,String location,String reporterId){}
@GetMapping public List<Complaint> all(){return repo.findAll();}
@PostMapping public Complaint create(@Valid @RequestBody NewComplaint n){Complaint c=new Complaint();c.setTitle(n.title());c.setDescription(n.description());c.setCategory(n.category());c.setPriority(n.priority());c.setLocation(n.location());c.setReporterId(n.reporterId());return repo.save(c);}
@PatchMapping("/{id}/status") public Complaint status(@PathVariable Long id,@RequestBody Map<String,String>b){Complaint c=repo.findById(id).orElseThrow();c.setStatus(b.getOrDefault("status","In Progress"));return repo.save(c);}
@PatchMapping("/{id}/assign") public Complaint assign(@PathVariable Long id,@RequestBody Map<String,String>b){Complaint c=repo.findById(id).orElseThrow();c.setAssignedTo(b.getOrDefault("assignedTo",""));return repo.save(c);}
@GetMapping("/summary") public Map<String,Long> summary(){List<Complaint>a=repo.findAll();return Map.of("total",(long)a.size(),"submitted",a.stream().filter(c->"Submitted".equals(c.getStatus())).count(),"inProgress",a.stream().filter(c->"In Progress".equals(c.getStatus())).count(),"resolved",a.stream().filter(c->"Resolved".equals(c.getStatus())||"Closed".equals(c.getStatus())).count());}
}