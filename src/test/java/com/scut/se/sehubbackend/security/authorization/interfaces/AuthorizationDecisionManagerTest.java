package com.scut.se.sehubbackend.security.authorization.interfaces;

import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class AuthorizationDecisionManagerTest {

    @Autowired AuthorizationDecisionManager authorizationDecisionManager;
    @Autowired DepartmentRepository departmentRepository;

    /**
     * 测试变更权限的权限
     */
    @Test
    public void testDecide() {
        _assertTrue(researchMinister,researchStaff, AuthorityEnum.LectureTicket);
        _assertFalse(researchMinister,researchStaff,AuthorityEnum.Etiquette);
        _assertFalse(standingCommittee,researchStaff,AuthorityEnum.LectureTicket);
        _assertFalse(secretaryMinister,researchStaff,AuthorityEnum.LectureTicket);
    }

    @Before
    public void setUp(){
        researchDepartment.addMember(researchMinister);
        researchDepartment.addMember(researchStaff);
        secretaryDepartment.addMember(secretaryMinister);
        standingCommitteeDepartment.addMember(standingCommittee);

        departmentRepository.saveAndFlush(researchDepartment);
        departmentRepository.saveAndFlush(secretaryDepartment);
        departmentRepository.saveAndFlush(standingCommitteeDepartment);
    }

    private void _assertTrue(Member operator,Member target,AuthorityEnum authorityEnum){ assertTrue(authorizationDecisionManager.decide(operator,target, authorityEnum)); }
    private void _assertFalse(Member operator,Member target,AuthorityEnum authorityEnum){ assertFalse(authorizationDecisionManager.decide(operator,target, authorityEnum)); }

    private Member researchMinister= Member.builder().position(PositionEnum.Minister).studentNumber(201730683314L).password("123").name("光度").build();
    private Member researchStaff= Member.builder().position(PositionEnum.Staff).studentNumber(201830662011L).password("123").name("光度").build();
    private Member secretaryMinister= Member.builder().position(PositionEnum.Minister).studentNumber(201918181919L).password("123").name("光度").build();
    private Member standingCommittee= Member.builder().position(PositionEnum.StandingCommittee).studentNumber(202077776666L).password("123").name("光度").build();
    private Department secretaryDepartment= Department.builder().departmentName(DepartmentNameEnum.Secretary).memberList(new ArrayList<>()).build();
    private Department researchDepartment=Department.builder().departmentName(DepartmentNameEnum.Research).memberList(new ArrayList<>()).build();
    private Department standingCommitteeDepartment=Department.builder().departmentName(DepartmentNameEnum.StandingCommittee).memberList(new ArrayList<>()).build();

}