package com.campuscare.repo;
import com.campuscare.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ComplaintRepository extends JpaRepository<Complaint,Long>{
 List<Complaint> findAllByOrderByCreatedAtDesc();
 List<Complaint> findBySubmittedByIdOrderByCreatedAtDesc(Long id);
 List<Complaint> findByAssignedStaffIdOrderByCreatedAtDesc(Long id);
}
