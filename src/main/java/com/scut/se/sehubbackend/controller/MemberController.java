package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.utils.ContextHelper;
import com.scut.se.sehubbackend.service.MemberService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final ContextHelper<Member> contextHelper;
    private final DTOUtil dtoUtil;

    public MemberController(MemberService memberService, ContextHelper<Member> contextHelper, DTOUtil dtoUtil) {
        this.memberService = memberService;
        this.contextHelper = contextHelper;
        this.dtoUtil = dtoUtil;
    }

    @GetMapping("/member")
    public MemberDTO getCurrent(){return dtoUtil.toDTO(contextHelper.getCurrentPrincipal());}

    @PutMapping("/member")
    public void update(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
    }
}
