package com.votesecure.onlinevoting.repository;
import com.votesecure.onlinevoting.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{}
