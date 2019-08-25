package com.scut.se.sehubbackend.service.MemberServiceTest;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.Role;
import com.scut.se.sehubbackend.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.LectureTicket;
import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.*;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Minister;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Staff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MemberServiceCreateTest {

    @Autowired DepartmentRepository departmentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    /**
     * <p>测试正常的创建，新用户,部员，部门存在</p>
     */
    @Test
    @WithMockUser(roles = "Admin")
    public void testCreateWithNewIdWithStaff() throws InvalidIdException {
        MemberDTO validNewMember=memberDTO(newStaffStudentNumber,existingDepartmentName,staff);
        memberService.create(validNewMember);
        verifyStaffNewMember();
    }



    /**
     * <p>测试正常的创建，新用户,部长，部门存在</p>
     */
    @Test
    @WithMockUser(roles = "Admin")
    public void testCreateWithNewIdWithMinister() throws InvalidIdException {
        MemberDTO validNewMember=memberDTO(newMinisterStudentNumber,existingDepartmentName,minister);
        memberService.create(validNewMember);
        verifyMinisterNewMember();
    }

    /**
     * <p>没有管理员权限的条件下创建，异常</p>
     */
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void testCreateWithNewIdWithoutAuthority() throws InvalidIdException {
        MemberDTO validNewMember=memberDTO(uselessStudentNumber1,existingDepartmentName,minister);
        memberService.create(validNewMember);
    }

    /**
     * <p>测试创建已存在的用户，异常</p>
     */
    @Test(expected = InvalidIdException.class)
    @WithMockUser(roles = "Admin")
    public void testCreateWithExistingId() throws InvalidIdException {
        MemberDTO newMemberWithExistingId=memberDTO(existingStudentNumber,existingDepartmentName,minister);
        memberService.create(newMemberWithExistingId);
    }

    /**
     * <p>测试创建用户所在的部门不存在，异常</p>
     */
    @Test(expected = InvalidIdException.class)
    @WithMockUser(roles = "Admin")
    public void testCreateWithNonExistingDepartment() throws InvalidIdException {
        MemberDTO newMemberWithNonExistingDepartment=memberDTO(uselessStudentNumber2,nonExistingDepartmentName,minister);
        memberService.create(newMemberWithNonExistingDepartment);
    }

    @Before
    public void setUp() {
        existingDepartment= Department.builder()
                .departmentName(existingDepartmentName)
                .memberList(new ArrayList<>())
                .build();
        Member existingMember= Member.builder()
                .studentNumber(existingStudentNumber)
                .password(defaultPassword)
                .name(commonName)
                .position(minister)
                .authorityList(new ArrayList<>())
                .build();
        existingDepartment.addMember(existingMember);
        departmentRepository.saveAndFlush(existingDepartment);
    }

    private void verifyStaffNewMember() {
        Member newMember=memberRepository.findById(newStaffStudentNumber).get();

        assertEquals(defaultPassword,newMember.getPassword());
        assertEquals(commonName,newMember.getName());
        assertEquals(staff,newMember.getPosition());
        assertEquals(existingDepartmentName,newMember.getDepartment().getDepartmentName());

        List<String> authorityStrings=authorityStrings(newMember);
        assertTrue(authorityStrings.contains(new Role(staff).getAuthority()));
        assertTrue(authorityStrings.contains(new Role(existingDepartmentName).getAuthority()));
        assertEquals(2,authorityStrings.size());
    }
    private void verifyMinisterNewMember() {
        Member newMember=memberRepository.findById(newMinisterStudentNumber).get();

        assertEquals(defaultPassword,newMember.getPassword());
        assertEquals(commonName,newMember.getName());
        assertEquals(minister,newMember.getPosition());
        assertEquals(existingDepartmentName,newMember.getDepartment().getDepartmentName());

        List<String> authorityStrings=authorityStrings(newMember);
        assertTrue(authorityStrings.contains(new Role(minister).getAuthority()));
        assertTrue(authorityStrings.contains(new Role(existingDepartmentName).getAuthority()));
        assertTrue(authorityStrings.contains(LectureTicket.toString()));
        assertEquals(3,authorityStrings.size());
    }
    private List<String> authorityStrings(Member member){
        List<Authority> authorities=member.getAuthorityList();
        List<String> authorityStrings=new ArrayList<>();
        for (Authority authority:authorities)
            authorityStrings.add(authority.getAuthorityName());
        return authorityStrings;
    }

    private MemberDTO memberDTO(Long studentNumber,DepartmentNameEnum departmentName,PositionEnum position){
        return MemberDTO.builder()
                .studentNumber(studentNumber)
                .name(commonName)
                .position(position)
                .departmentName(departmentName)
                .build();
    }
    Department existingDepartment;
    DepartmentNameEnum existingDepartmentName=Research;
    DepartmentNameEnum nonExistingDepartmentName=Life;

    Long existingStudentNumber=9999999L;
    Long uselessStudentNumber1 =666630662011L;
    Long uselessStudentNumber2 =888830662011L;
    Long newStaffStudentNumber =333330662011L;
    Long newMinisterStudentNumber =777730662011L;

    String commonName="光度";
    @Autowired@Qualifier("defaultPassword") String defaultPassword;
    PositionEnum minister =Minister;
    PositionEnum staff=Staff;
}