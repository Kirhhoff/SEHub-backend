package com.scut.se.sehubbackend.dao.user;

import com.scut.se.sehubbackend.domain.user.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserHistoryRepository extends JpaRepository<UserHistory,Long> {
}