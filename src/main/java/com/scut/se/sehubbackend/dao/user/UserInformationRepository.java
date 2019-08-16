package com.scut.se.sehubbackend.dao.user;

import com.scut.se.sehubbackend.domain.user.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserInformationRepository extends JpaRepository<UserInformation,Long> {
}
