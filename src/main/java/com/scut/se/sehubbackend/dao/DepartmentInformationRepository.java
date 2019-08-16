package com.scut.se.sehubbackend.dao;

import com.scut.se.sehubbackend.domain.DepartmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DepartmentInformationRepository extends JpaRepository<DepartmentInformation, Long> {
}
