package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MemberServiceTest {

    @MockBean MemberContextHelper memberContextHelper;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /**
     * <p>测试对自己个人信息的更新</p>
     * <p>原则上来说，只有姓名、手机、email可以更新</p>
     * <p>传递来的数据除学号外每一项都与本来的数据不同，测试确保这几项是被修改了的</p>
     */
    @Test
    @Transactional
    public void testUpdateSelf() {
        setUpCurrentMember(currentMember);
        memberService.update(memberDTO);
        verifyMutableData();
        verifyImmutableData();
    }

    /**
     * <p>测试对他人个人信息的更新</p>
     * <p>不允许</p>
     */
    @Test(expected = AccessDeniedException.class)
    public void testUpdateOthers(){
        setUpCurrentMember(otherMember);
        memberService.update(memberDTO);
    }

    @Before
    public void setUp(){
        initialDepartment= Department.builder().departmentName(initialDepartmentName).memberList(new ArrayList<>()).build();
        newDepartment= Department.builder().departmentName(newDepartmentName).memberList(new ArrayList<>()).build();

        currentMember= Member.builder()
                .studentNumber(201730683314L)
                .password("123456")
                .name("彭天祥")
                .position(PositionEnum.Minister)
                .phoneNumber("15564278737")
                .email("1070280566@qq.com")
                .authorityList(new ArrayList<>())
                .build();
        otherMember=Member.builder()
                .studentNumber(201830662011L)
                .password("785678")
                .name("光度")
                .position(PositionEnum.Minister)
                .phoneNumber("15521093401")
                .email("15564278737@163.com")
                .authorityList(new ArrayList<>())
                .build();

        initialDepartment.addMember(currentMember);
        newDepartment.addMember(otherMember);

        departmentRepository.saveAndFlush(initialDepartment);
        departmentRepository.saveAndFlush(newDepartment);

        List<String> authorities=new ArrayList<>();
        authorities.add("认真系列");
        memberDTO=MemberDTO.builder()
                .studentNumber(201730683314L)
                .name("连续普通拳")
                .position(PositionEnum.Staff)
                .phoneNumber("15588039521")
                .email("15564278737@qq.com")
                .departmentName(newDepartmentName)
                .authorityList(authorities)
                .build();
    }
    private void setUpCurrentMember(Member currentMember){ doReturn(currentMember).when(memberContextHelper).getCurrentPrincipal(); }
    private void verifyMutableData(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals(memberDTO.getName(),memberAfterAlter.getName());
        assertEquals(memberDTO.getPhoneNumber(),memberAfterAlter.getPhoneNumber());
        assertEquals(memberDTO.getEmail(),memberAfterAlter.getEmail());
    }
    private void verifyImmutableData(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals(PositionEnum.Minister,memberAfterAlter.getPosition());
        assertEquals(initialDepartment,memberAfterAlter.getDepartment());
        assertEquals(new ArrayList<>(),memberAfterAlter.getAuthorityList());
    }

    Member currentMember;
    Member otherMember;
    MemberDTO memberDTO;

    Department initialDepartment;
    Department newDepartment;

    DepartmentNameEnum initialDepartmentName=DepartmentNameEnum.Research;
    DepartmentNameEnum newDepartmentName=DepartmentNameEnum.Quality;

}