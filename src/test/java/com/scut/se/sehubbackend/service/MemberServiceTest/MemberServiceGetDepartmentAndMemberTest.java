package com.scut.se.sehubbackend.service.MemberServiceTest;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.Research;
import static com.scut.se.sehubbackend.enumeration.DepartmentNameEnum.Secretary;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Admin;
import static com.scut.se.sehubbackend.enumeration.PositionEnum.Staff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase()
public class MemberServiceGetDepartmentAndMemberTest {

    @Autowired MemberService memberService;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired MemberRepository memberRepository;

    /**
     * <p>测试有权限时候的访问，返回所有部门名和所有Member</p>
     */
    @Test
    @WithMockUser(roles = "Admin")
    public void testWithAdminAuthority() {
        verifyReturnData(memberService.getAllDepartmentNameAndAllMember());
    }

    /**
     * <p>测试没有管理员权限，拒绝访问</p>
     */
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void testWithoutAdminAuthority(){
        memberService.getAllDepartmentNameAndAllMember();
    }

    @Before
    public void setUp() {
        memberRepository.deleteAll();
        Department department1= Department.builder().departmentName(departmentName1).memberList(new ArrayList<>()).build();
        Department department2= Department.builder().departmentName(departmentName2).memberList(new ArrayList<>()).build();
        Member member1= Member.builder().studentNumber(id1).password(password).name(name).position(position).authorityList(new ArrayList<>()).build();
        Member member2= Member.builder().studentNumber(id2).password(password).name(name).position(position).authorityList(new ArrayList<>()).build();
        Member admin=Member.builder().studentNumber(idAdmin).password(password).name(name).position(Admin).authorityList(new ArrayList<>()).build();
        department1.addMember(member1);
        department2.addMember(member2);
        departmentRepository.saveAndFlush(department1);
        departmentRepository.saveAndFlush(department2);

        memberDTO1= MemberDTO.builder().studentNumber(id1).name(name).position(position).departmentName(departmentName1).authorityList(new ArrayList<>()).build();
        memberDTO2= MemberDTO.builder().studentNumber(id2).name(name).position(position).departmentName(departmentName2).authorityList(new ArrayList<>()).build();
    }

    private void verifyReturnData(Map<String,Object> map){
        List<DepartmentNameEnum> names= (List<DepartmentNameEnum>) map.get("allDepartment");
        List<MemberDTO> members= (List<MemberDTO>) map.get("allMember");

        assertEquals(Arrays.asList(DepartmentNameEnum.values()),names);
        assertTrue(members.contains(memberDTO1));
        assertTrue(members.contains(memberDTO2));
        assertEquals(2,members.size());
    }
    private DepartmentNameEnum departmentName1=Research;
    private DepartmentNameEnum departmentName2=Secretary;
    private Long id1=9991111111465L;
    private Long id2=554354314L;
    private Long idAdmin=340121314L;

    private String name="光度";
    private String password="777";
    private PositionEnum position=Staff;

    private MemberDTO memberDTO1;
    private MemberDTO memberDTO2;

}