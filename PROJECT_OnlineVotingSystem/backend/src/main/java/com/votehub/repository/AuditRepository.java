package com.votehub.repository; import com.votehub.entity.*; import org.springframework.data.jpa.repository.JpaRepository; public interface AuditRepository extends JpaRepository<AuditLog,Long>{}
