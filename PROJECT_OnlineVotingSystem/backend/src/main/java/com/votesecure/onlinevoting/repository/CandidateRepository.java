package com.votesecure.onlinevoting.repository;
import com.votesecure.onlinevoting.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CandidateRepository extends JpaRepository<Candidate,Long>{List<Candidate> findByActiveTrue(); long countByActiveTrue();}
