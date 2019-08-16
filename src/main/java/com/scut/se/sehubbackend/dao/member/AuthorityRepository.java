package com.scut.se.sehubbackend.dao.member;

import com.scut.se.sehubbackend.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
