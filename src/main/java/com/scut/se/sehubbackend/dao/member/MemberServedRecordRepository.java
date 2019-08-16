package com.scut.se.sehubbackend.dao.member;

import com.scut.se.sehubbackend.domain.member.MemberServingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberServedRecordRepository extends JpaRepository<MemberServingRecord, Long> {
}
