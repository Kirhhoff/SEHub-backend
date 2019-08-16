package com.scut.se.sehubbackend.dao.user;

import com.scut.se.sehubbackend.domain.user.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication,String> {
}
