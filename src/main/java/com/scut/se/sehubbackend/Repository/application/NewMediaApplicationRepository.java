package com.scut.se.sehubbackend.Repository.application;

import com.scut.se.sehubbackend.Domain.application.NewMediaApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewMediaApplicationRepository extends JpaRepository<NewMediaApplication,Long> {
}