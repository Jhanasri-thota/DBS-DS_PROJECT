package com.votesecure.onlinevoting.controller;

import com.votesecure.onlinevoting.model.*;
import com.votesecure.onlinevoting.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoterRepository voters; private final CandidateRepository candidates; private final VoteRepository votes; private final ElectionRepository elections;
    public VoteController(VoterRepository voters,CandidateRepository candidates,VoteRepository votes,ElectionRepository elections){this.voters=voters;this.candidates=candidates;this.votes=votes;this.elections=elections;}
    record Req(Long candidateId){}

    @PostMapping
    public ResponseEntity<?> vote(@RequestHeader(value="Authorization",required=false) String auth,@RequestBody Req req){
        if(auth==null||!auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("message","Authentication required"));
        String voterId=auth.substring(7).trim();
        Voter voter=voters.findByVoterId(voterId).orElse(null);
        if(voter==null||!"ACTIVE".equals(voter.getAccountStatus())||!voter.isOtpVerified()) return ResponseEntity.status(401).body(Map.of("message","Complete registration and OTP verification first"));
        ElectionSetting election=elections.findAll().stream().findFirst().orElse(null);
        if(election==null||!"OPEN".equalsIgnoreCase(election.getStatus())||LocalDateTime.now().isBefore(election.getStartTime())||LocalDateTime.now().isAfter(election.getEndTime())) return ResponseEntity.status(403).body(Map.of("message","Voting is currently closed"));
        if(voter.isHasVoted()||votes.findByVoterId(voter.getId()).isPresent()) return ResponseEntity.status(409).body(Map.of("message","This voter has already voted"));
        Candidate candidate=candidates.findById(req.candidateId()).orElse(null);
        if(candidate==null||!candidate.isActive()) return ResponseEntity.badRequest().body(Map.of("message","Candidate not found or inactive"));
        Vote vote=new Vote(); vote.setVoter(voter); vote.setCandidate(candidate); vote.setVotedAt(LocalDateTime.now()); vote.setReceiptCode("VR-2026-"+UUID.randomUUID().toString().substring(0,8).toUpperCase()); votes.save(vote);
        voter.setHasVoted(true); voters.save(voter);
        return ResponseEntity.ok(Map.of("receiptCode",vote.getReceiptCode(),"votedAt",vote.getVotedAt().toString(),"status","RECORDED"));
    }
}
