package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Long id) throws InvalidIdException { return memberRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(Member member){memberRepository.saveAndFlush(member);}
}
