package com.votesecure.onlinevoting.service;

import com.votesecure.onlinevoting.model.ElectionSetting;
import com.votesecure.onlinevoting.repository.CandidateRepository;
import com.votesecure.onlinevoting.repository.ElectionRepository;
import com.votesecure.onlinevoting.repository.VoterRepository;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ElectionService {
    private final ElectionRepository elections; private final CandidateRepository candidates; private final VoterRepository voters;
    public ElectionService(ElectionRepository e,CandidateRepository c,VoterRepository v){elections=e;candidates=c;voters=v;}
    public Map<String,Object> summary(){
        ElectionSetting e=elections.findAll().stream().findFirst().orElse(null);
        return Map.of("registeredVoters",voters.countByAccountStatus("ACTIVE"),"pendingProfiles",voters.countByAccountStatus("PENDING"),"candidateCount",candidates.countByActiveTrue(),"election",e==null?"NONE":e.getElectionName());
    }
}
