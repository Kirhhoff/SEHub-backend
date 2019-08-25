package com.scut.se.sehubbackend.service.MemberServiceTest;

import com.scut.se.sehubbackend.dao.member.AuthorityRepository;
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
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.scut.se.sehubbackend.enumeration.AuthorityEnum.LectureTicket;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Minister;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Staff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MemberServiceUpdateTest {

    @MockBean MemberContextHelper memberContextHelper;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired AuthorityRepository authorityRepository;

    /**
     * <p>测试对自己个人信息的更新</p>
     * <p>原则上来说，只有姓名、手机、email可以更新</p>
     * <p>传递来的数据除学号外每一项都与本来的数据不同，测试确保这几项是被修改了的</p>
     */
    @Test
    @Transactional
    public void testUpdateSelf() {
        setUp();
        setUpCurrentMember(currentMember);
        memberService.update(memberDTO);
        verifyMutableDataForUpdate();
        verifyImmutableDataForUpdate();
        tearDown();
    }

    /**
     * <p>测试对他人个人信息的更新</p>
     * <p>不允许</p>
     */
    @Test(expected = AccessDeniedException.class)
    public void testUpdateOthers(){
        setUp();
        setUpCurrentMember(otherMember);
        memberService.update(memberDTO);
        tearDown();
    }

    @Test
    @Transactional
    @WithMockUser(roles = "Admin")
    public void testModifyWithAdmin() throws InvalidIdException {
        setUp();
        memberService.modify(memberDTO);
        verifyMutableDataForModification();
        verifyImmutableDataForModification();
        tearDown();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void testModifyWithoutAdmin() throws InvalidIdException {
        setUp();
        memberService.modify(memberDTO);
        tearDown();
    }

    @Test(expected = InvalidIdException.class)
    @WithMockUser(roles = "Admin")
    public void testModifyWithNonExistingId() throws InvalidIdException {
        setUp();
        memberService.modify(nonExistingMember);
        tearDown();
    }

    public void setUp(){
        initialDepartment= Department.builder().departmentName(initialDepartmentName).memberList(new ArrayList<>()).build();
        newDepartment= Department.builder().departmentName(newDepartmentName).memberList(new ArrayList<>()).build();

        currentMember= Member.builder()
                .studentNumber(201730683314L)
                .password("123456")
                .name("彭天祥")
                .position(Minister)
                .phoneNumber("15564278737")
                .email("1070280566@qq.com")
                .authorityList(new ArrayList<>())
                .build();
        otherMember=Member.builder()
                .studentNumber(201830662011L)
                .password("785678")
                .name("光度")
                .position(Minister)
                .phoneNumber("15521093401")
                .email("15564278737@163.com")
                .authorityList(new ArrayList<>())
                .build();
        currentMember.addAuthority(Authority.builder().authorityName(new Role(Minister).getAuthority()).build());
        currentMember.addAuthority(Authority.builder().authorityName(new Role(initialDepartmentName).getAuthority()).build());
        currentMember.addAuthority(Authority.builder().authorityName(LectureTicket.toString()).build());

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

    public void tearDown(){
        authorityRepository.deleteAll();
        memberRepository.deleteAll();
        departmentRepository.deleteAll();
    }
    private void setUpCurrentMember(Member currentMember){ doReturn(currentMember).when(memberContextHelper).getCurrentPrincipal(); }
    private void verifyMutableDataForUpdate(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals(memberDTO.getName(),memberAfterAlter.getName());
        assertEquals(memberDTO.getPhoneNumber(),memberAfterAlter.getPhoneNumber());
        assertEquals(memberDTO.getEmail(),memberAfterAlter.getEmail());
    }
    private void verifyImmutableDataForUpdate(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals(Minister,memberAfterAlter.getPosition());
        assertEquals(initialDepartmentName,memberAfterAlter.getDepartment().getDepartmentName());
        assertEquals("123456",memberAfterAlter.getPassword());

        List<String> authorityStrings=authorityStrings(memberAfterAlter);
        assertTrue(authorityStrings.contains("ROLE_Research"));
        assertTrue(authorityStrings.contains("ROLE_Minister"));
        assertTrue(authorityStrings.contains("LectureTicket"));
        assertEquals(3,authorityStrings.size());
    }

    private void verifyMutableDataForModification(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals(memberDTO.getName(),memberAfterAlter.getName());
        assertEquals(Staff,memberAfterAlter.getPosition());
        assertEquals(newDepartmentName,memberAfterAlter.getDepartment().getDepartmentName());

        List<String> authorityStrings=authorityStrings(memberAfterAlter);
        assertTrue(authorityStrings.contains("ROLE_Quality"));
        assertTrue(authorityStrings.contains("ROLE_Staff"));
        assertEquals(2,authorityStrings.size());
    }

    private void verifyImmutableDataForModification(){
        Member memberAfterAlter=memberRepository.findById(currentMember.getStudentNumber()).get();
        assertEquals("15564278737",memberAfterAlter.getPhoneNumber());
        assertEquals("1070280566@qq.com",memberAfterAlter.getEmail());
        assertEquals("123456",memberAfterAlter.getPassword());
    }

    private List<String> authorityStrings(Member member){
        List<Authority> authorities=member.getAuthorityList();
        List<String> authorityStrings=new ArrayList<>();
        for (Authority authority:authorities)
            authorityStrings.add(authority.getAuthorityName());
        return authorityStrings;
    }

    Member currentMember;
    Member otherMember;
    MemberDTO memberDTO;
    MemberDTO nonExistingMember= MemberDTO.builder().studentNumber(5554524L).build();

    Department initialDepartment;
    Department newDepartment;

    DepartmentNameEnum initialDepartmentName=DepartmentNameEnum.Research;
    DepartmentNameEnum newDepartmentName=DepartmentNameEnum.Quality;
}