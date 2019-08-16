package com.scut.se.sehubbackend.dao.memberN;

import com.scut.se.sehubbackend.domain.memberN.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
