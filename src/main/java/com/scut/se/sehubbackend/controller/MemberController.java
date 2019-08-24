package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PutMapping("/member")
    public void update(MemberDTO memberDTO) {

    }
}
