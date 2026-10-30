package com.campuscare.repo;
import com.campuscare.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface NoticeRepository extends JpaRepository<Notice,Long>{ List<Notice> findByUserIdOrderByCreatedAtDesc(Long id); }
