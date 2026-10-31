package com.campuscure.repo;
import com.campuscure.model.AppUser;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByUniversityId(String id);boolean existsByUniversityId(String id);boolean existsByEmail(String email);}