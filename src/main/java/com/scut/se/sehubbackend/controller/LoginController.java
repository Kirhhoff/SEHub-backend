package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired ContextHelper<Member> contextHelper;
    @Autowired DTOUtil dtoUtil;

    @PostMapping
    public MemberDTO login(){ return dtoUtil.toDTO(contextHelper.getCurrentPrincipal()); }

}
