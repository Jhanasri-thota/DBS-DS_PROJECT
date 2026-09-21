package com.votesecure.onlinevoting.repository;

import com.votesecure.onlinevoting.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    Optional<Voter> findByVoterId(String voterId);
    Optional<Voter> findByEmail(String email);
    Optional<Voter> findByPhone(String phone);
    long countByAccountStatus(String accountStatus);
}
