package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.security.Role;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.*;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Admin;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Minister;

@Service
public class MemberService {

    final MemberRepository memberRepository;
    final ContextHelper<Member> contextHelper;
    final DepartmentService departmentService;
    final String defaultPassword;
    final DTOUtil dtoUtil;

    public MemberService(MemberRepository memberRepository, ContextHelper<Member> contextHelper, DepartmentService departmentService, @Qualifier("defaultPassword") String defaultPassword, DTOUtil dtoUtil) {
        this.memberRepository = memberRepository;
        this.contextHelper = contextHelper;
        this.departmentService = departmentService;
        this.defaultPassword = defaultPassword;
        this.dtoUtil = dtoUtil;
    }

    /**
     * <p>从用户的角度更新一个人的信息，可更新的包括姓名、手机、邮箱</p>
     * @param memberDTO 请求中的用户信息
     */
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

    /**
     * <p>创建一个新用户，管理员才能执行</p>
     * @param memberDTO 新用户的信息，需包括学号、姓名、职位、部门
     */
    @PreAuthorize("hasRole('Admin')")
    public void create(MemberDTO memberDTO) throws InvalidIdException {
        Long studentNumber=memberDTO.getStudentNumber();
        try {
            findById(studentNumber);
        } catch (InvalidIdException e) {
            Department department=departmentService.findById(memberDTO.getDepartmentName());
            PositionEnum position=memberDTO.getPosition();
            if(position==Admin)
                throw new InvalidIdException();

            Member member= Member.builder()
                    .studentNumber(studentNumber)
                    .password(defaultPassword)
                    .name(memberDTO.getName())
                    .position(position)
                    .authorityList(new ArrayList<>())
                    .build();

            List<Authority> authorities=buildAuthority(department.getDepartmentName(),position);
            for (Authority authority:authorities)
                member.addAuthority(authority);

            department.addMember(member);
            departmentService.save(department);
            return;
        }
        throw new InvalidIdException();
    }

    @PreAuthorize("hasRole('Admin')")
    public Map<String,Object> getAllDepartmentNameAndAllMember(){
        Map<String,Object> data=new HashMap<>();
        data.put("allDepartment", Arrays.asList(DepartmentNameEnum.values()));

        List<Member> allMemberExceptAdmin=memberRepository.findAllByPositionNot(Admin);
        List<MemberDTO> allMemberDTO=new ArrayList<>();
        for (Member member:allMemberExceptAdmin)
            allMemberDTO.add(dtoUtil.toDTO(member));
        data.put("allMember",allMemberDTO);
        return data;
    }

    public Member findById(Long id) throws InvalidIdException { return memberRepository.findById(id).orElseThrow(InvalidIdException::new); }
    public void save(Member member){memberRepository.saveAndFlush(member);}

    private List<Authority> buildAuthority(DepartmentNameEnum department, PositionEnum position){
        List<Authority> authorities=new ArrayList<>();
        authorities.add(Authority.builder().authorityName(new Role(department).getAuthority()).build());
        authorities.add(Authority.builder().authorityName(new Role(position).getAuthority()).build());
        if(position==Minister){
            switch (department){
                case Relation:authorities.add(Authority.builder().authorityName(Etiquette.toString()).build());
                    break;
                case Secretary:authorities.add(Authority.builder().authorityName(Host.toString()).build());
                    break;
                case Research:authorities.add(Authority.builder().authorityName(LectureTicket.toString()).build());
                    break;
                case Propaganda:authorities.add(Authority.builder().authorityName(Poster.toString()).build());
                    break;
            }
        }
        return authorities;
    }
}
