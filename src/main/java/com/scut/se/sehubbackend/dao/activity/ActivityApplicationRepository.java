package com.scut.se.sehubbackend.dao.activity;

import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {
}
