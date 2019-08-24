package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    final MemberRepository memberRepository;
    final ContextHelper<Member> contextHelper;

    public MemberService(MemberRepository memberRepository, ContextHelper<Member> contextHelper) {
        this.memberRepository = memberRepository;
        this.contextHelper = contextHelper;
    }

    public void update(MemberDTO memberDTO){
        Member currentMember=contextHelper.getCurrentPrincipal();
        if (!currentMember.getStudentNumber().equals(memberDTO.getStudentNumber()))
            throw new AccessDeniedException("");
        else {
            currentMember.setName(memberDTO.getName());
            currentMember.setPhoneNumber(memberDTO.getPhoneNumber());
            currentMember.setEmail(memberDTO.getEmail());
            save(currentMember);
        }
    }

    public Member findById(Long id) throws InvalidIdException { return memberRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(Member member){memberRepository.saveAndFlush(member);}
}
