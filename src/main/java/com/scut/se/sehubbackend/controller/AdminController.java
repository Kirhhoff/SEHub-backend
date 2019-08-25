package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.DepartmentService;
import com.scut.se.sehubbackend.service.MemberService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {

    @Autowired MemberService memberService;
    @Autowired DepartmentService departmentService;
    @Autowired DTOUtil dtoUtil;

    @GetMapping("/department")
    public List<String> getAllCurrentlyExistingDepartmentName(){ return departmentService.getAllCurrentlyExistingDepartmentName(); }

    @RequestMapping(path = "/department/{departmentName}",method = RequestMethod.GET)
    public DepartmentDTO getDepartmentByName(@PathVariable("departmentName")DepartmentNameEnum departmentName) throws InvalidIdException {
        return dtoUtil.toDTO(departmentService.findById(departmentName));
    }

    @GetMapping("/member")
    public Map<String,Object> getAllDepartmentAndAllMember(){
        return memberService.getAllDepartmentNameAndAllMember();
    }

    @PostMapping("/member")
    public void createMember(@RequestBody MemberDTO memberDTO) throws InvalidIdException {
        memberService.create(memberDTO);
    }

    @PutMapping("/member")
    public void modifyMember(@RequestBody MemberDTO memberDTO) throws InvalidIdException {
        memberService.modify(memberDTO);
    }

    @RequestMapping(value = "/member/{id}",method = RequestMethod.DELETE)
    public void deleteMember(@PathVariable("id")Long id){
        memberService.delete(id);
    }
}

