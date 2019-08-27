package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.dao.activity.ActivityApplicationRepository;
import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.domain.activity.ActivitySupplementaryInfo;
import com.scut.se.sehubbackend.domain.activity.CheckInfo;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>对活动申请表dao自定义api测试</p>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ActivityApplicationRepositoryTest {

    @Autowired ActivityApplicationRepository activityApplicationRepository;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired MemberRepository memberRepository;

    /**
     * <p>测试{@link ActivityApplicationRepository#findAllByDepartment(Department)}方法</p>
     * <p>创建了两个部门调研部和秘书部，调研部一个部长一个部员，秘书部一个部长，每个人各发起过一次活动申请</p>
     * <p>verify api能够根据输入调研部选出且仅选出调研部的两张申请表</p>
     */
    @Test
    public void testFindAllByDepartment(){

        List<ActivityApplication> queryResult=activityApplicationRepository
                .findAllByDepartment(researchDepartment);

        verifyResult(queryResult);
    }

    @Before
    public void before(){
        createDepartment();
        createTwoMember();
        createAnActivityApplicationForEachMember();
        commitCreation();
    }

    private void createDepartment(){
        researchDepartment= Department.builder()
                .departmentName(DepartmentNameEnum.Research)
                .departmentDescription("")
                .memberList(new ArrayList<>())
                .build();

        secretaryDepartment=Department.builder()
                .departmentName(DepartmentNameEnum.Secretary)
                .departmentDescription("")
                .memberList(new ArrayList<>())
                .build();
    }
    private void createTwoMember(){
        luminosity=Member.builder()
                .studentNumber(201730683314L)
                .password("77777777")
                .name("彭天祥")
                .position(PositionEnum.Minister)
                .authorityList(new ArrayList<>())
                .build();
        xingo=Member.builder()
                .studentNumber(201830662011L)
                .password("777")
                .name("刘逸曦")
                .position(PositionEnum.Staff)
                .authorityList(new ArrayList<>())
                .build();
        haoGe=Member.builder()
                .studentNumber(201730683318L)
                .password("77")
                .name("申浩")
                .position(PositionEnum.Minister)
                .authorityList(new ArrayList<>())
                .build();

        researchDepartment.addMember(luminosity);
        researchDepartment.addMember(xingo);
        secretaryDepartment.addMember(haoGe);
    }

    private void createAnActivityApplicationForEachMember(){
        researchApplication1 =ActivityApplication.builder()
                .activityBasicInfo(new ActivityBasicInfo())
                .activitySupplementaryInfo(new ActivitySupplementaryInfo())
                .checkInfo(
                        CheckInfo.builder()
                                .initializer(luminosity)
                                .checkDate(null)
                                .checkFeedback(null)
                                .checkStatus(CheckStatusEnum.WAIT)
                                .submissionDate(new Date())
                                .lastModifier(luminosity)
                                .build()
                )
                .build();
        researchApplication2 =ActivityApplication.builder()
                .activityBasicInfo(new ActivityBasicInfo())
                .activitySupplementaryInfo(new ActivitySupplementaryInfo())
                .checkInfo(
                        CheckInfo.builder()
                                .initializer(xingo)
                                .checkDate(new Date())
                                .checkFeedback("炒鸡棒！")
                                .checkStatus(CheckStatusEnum.PASS)
                                .submissionDate(new Date())
                                .lastModifier(luminosity)
                                .build()
                )
                .build();
        secretaryApplication=ActivityApplication.builder()
                .activityBasicInfo(new ActivityBasicInfo())
                .activitySupplementaryInfo(new ActivitySupplementaryInfo())
                .checkInfo(
                        CheckInfo.builder()
                                .initializer(haoGe)
                                .checkDate(new Date())
                                .checkFeedback("有点蛋疼啊兄弟")
                                .checkStatus(CheckStatusEnum.NOPASS)
                                .submissionDate(new Date())
                                .lastModifier(haoGe)
                                .build()
                )
                .build();
    }

    /**
     * <p>提交所有数据库的改动</p>
     * <p>此处必须使用{@code saveAndFlush}方法，因为测试的dao api是自定义的query，查询之前不会自动将未提交的改动提交</p>
     */
    private void commitCreation(){
        departmentRepository.saveAndFlush(researchDepartment);
        departmentRepository.saveAndFlush(secretaryDepartment);

        memberRepository.saveAndFlush(luminosity);
        memberRepository.saveAndFlush(xingo);
        memberRepository.saveAndFlush(haoGe);

        activityApplicationRepository.saveAndFlush(researchApplication1);
        activityApplicationRepository.saveAndFlush(researchApplication2);
        activityApplicationRepository.saveAndFlush(secretaryApplication);
    }
    private void verifyResult(List<ActivityApplication> queryResult){
        assertNotNull(queryResult);
        assertEquals(2,queryResult.size());
        assertTrue(queryResult.contains(researchApplication1));
        assertTrue(queryResult.contains(researchApplication2));
        assertFalse(queryResult.contains(secretaryApplication));
    }
    private Department researchDepartment;
    private Member luminosity;
    private Member xingo;

    private Department secretaryDepartment;
    private Member haoGe;

    private ActivityApplication researchApplication1;
    private ActivityApplication researchApplication2;

    private ActivityApplication secretaryApplication;

    @After
    public void tearDown() {
        departmentRepository.deleteAll();
        activityApplicationRepository.deleteAll();
    }
}
