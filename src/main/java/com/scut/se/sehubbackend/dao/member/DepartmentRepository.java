package com.scut.se.sehubbackend.dao.member;

import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, DepartmentNameEnum> {
}
