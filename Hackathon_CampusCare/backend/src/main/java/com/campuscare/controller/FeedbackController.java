package com.campuscare.controller;
import com.campuscare.model.*;import com.campuscare.repo.*;import com.campuscare.security.AuthSupport;
import jakarta.servlet.http.HttpServletRequest;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/feedback")
public class FeedbackController {
 private final FeedbackRepository feedback;private final ComplaintRepository complaints;private final AuthSupport auth;
 public FeedbackController(FeedbackRepository f,ComplaintRepository c,AuthSupport a){feedback=f;complaints=c;auth=a;}
 public record Input(int rating,String comment){}
 @PostMapping("/{complaintId}") public Object create(@PathVariable Long complaintId,@RequestBody Input x,HttpServletRequest r){User u=auth.current(r);Complaint c=complaints.findById(complaintId).orElseThrow();if(!c.getSubmittedBy().getId().equals(u.getId())||!c.getStatus().equals("RESOLVED"))throw new IllegalArgumentException("Feedback is available for your resolved complaints");if(x.rating()<1||x.rating()>5)throw new IllegalArgumentException("Rating must be from 1 to 5");if(feedback.existsByComplaintId(complaintId))throw new IllegalArgumentException("Feedback already submitted");Feedback f=new Feedback();f.setComplaint(c);f.setUser(u);f.setRating(x.rating());f.setComment(x.comment());return feedback.save(f);}
}
