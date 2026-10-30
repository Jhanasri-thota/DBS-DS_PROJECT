package com.campuscare.repo;
import com.campuscare.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department,Long>{}
