package com.scut.se.sehubbackend.Repository.memberN;

import com.scut.se.sehubbackend.Domain.memberN.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
