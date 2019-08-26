package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.service.MemberService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired ContextHelper<Member> contextHelper;
    @Autowired DTOUtil dtoUtil;
    @GetMapping("/member")
    public MemberDTO getCurrent(){return dtoUtil.toDTO(contextHelper.getCurrentPrincipal());}

    @PutMapping("/member")
    public void update(MemberDTO memberDTO) {
        memberService.update(memberDTO);
    }
}
