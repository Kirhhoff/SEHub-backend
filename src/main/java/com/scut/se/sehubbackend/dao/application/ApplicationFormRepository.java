package com.scut.se.sehubbackend.dao.application;

import com.scut.se.sehubbackend.domain.application.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm,Long> {
}
