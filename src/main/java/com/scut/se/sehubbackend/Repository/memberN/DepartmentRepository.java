package com.scut.se.sehubbackend.Repository.memberN;

import com.scut.se.sehubbackend.Domain.memberN.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
