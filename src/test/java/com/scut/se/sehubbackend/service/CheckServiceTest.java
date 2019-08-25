package com.scut.se.sehubbackend.service;

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
import com.scut.se.sehubbackend.exception.CheckHasBeenOperatedException;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
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
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class CheckServiceTest {

    @MockBean MemberContextHelper memberContextHelper;
    @Autowired ActivityApplicationService activityApplicationService;
    @Autowired CheckService checkService;
    @Autowired MemberRepository memberRepository;
    @Autowired DepartmentRepository departmentRepository;

    /**
     * 测试没有权限
     * @throws CheckHasBeenOperatedException
     * @throws InvalidIdException
     */
    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void testCheckWithoutAuthority() throws CheckHasBeenOperatedException, InvalidIdException {
        setUpWaitingStatus();
        checkService.activityCheck(activity.getId(),true,feedback);
    }

    /**
     * 测试有权限但已经审核过
     * @throws CheckHasBeenOperatedException
     * @throws InvalidIdException
     */
    @Test(expected = CheckHasBeenOperatedException.class)
    @WithMockUser(roles = "StandingCommittee")
    public void testActivityCheckWithChecked() throws CheckHasBeenOperatedException, InvalidIdException {
        setUpCheckedStatus();
        checkService.activityCheck(activity.getId(),true,feedback);
    }

    /**
     * 测试正常的审核
     * @throws CheckHasBeenOperatedException
     * @throws InvalidIdException
     */
    @Test
    @WithMockUser(roles = "StandingCommittee")
    @Transactional
    public void testActivityCheckWithWaiting() throws CheckHasBeenOperatedException, InvalidIdException {
        setUpWaitingStatus();
        checkService.activityCheck(activity.getId(),true,feedback);
        verifySuccessfulCheck();
    }



    @Before
    public void before(){
        Department department=Department.builder().departmentName(DepartmentNameEnum.Research).memberList(new ArrayList<>()).build();
        checker = Member.builder().studentNumber(201730683314L).password("123").name("光度").position(PositionEnum.Minister).authorityList(new ArrayList<>()).build();
        requester=Member.builder().studentNumber(201830662011L).password("123").name("光度").position(PositionEnum.Minister).authorityList(new ArrayList<>()).build();
        department.addMember(checker);
        department.addMember(requester);
        departmentRepository.saveAndFlush(department);

        submissionDate=new Date();

        checked.setInitializer(requester);
        checked.setLastModifier(checker);
        checked.setSubmissionDate(submissionDate);
        checked.setCheckDate(new Date());

        waiting.setInitializer(requester);
        waiting.setLastModifier(requester);
        waiting.setSubmissionDate(submissionDate);

        doReturn(checker).when(memberContextHelper).getCurrentPrincipal();
    }

    private Member checker;
    private Member requester;

    private void setUpCheckedStatus(){
        activity.setCheckInfo(checked);
        activityApplicationService.save(activity);
    }
    private void setUpWaitingStatus(){
        activity.setCheckInfo(waiting);
        activityApplicationService.save(activity);
    }

    private void verifySuccessfulCheck() throws InvalidIdException {
        CheckInfo afterCheck=activityApplicationService.findById(activity.getId()).getCheckInfo();

        assertEquals(CheckStatusEnum.PASS,afterCheck.getCheckStatus());
        assertEquals(feedback,afterCheck.getCheckFeedback());
        assertTrue(new Date().after(afterCheck.getCheckDate()));
        assertEquals(checker,afterCheck.getLastModifier());
    }

    private ActivityApplication activity=ActivityApplication.builder()
            .activityBasicInfo(ActivityBasicInfo.builder()
                    .name("不可思议的nb活动")
                    .build())
            .activitySupplementaryInfo(ActivitySupplementaryInfo.builder()
                    .organizer("窝工")
                    .build())
            .build();

    private Date submissionDate;
    private CheckInfo checked = CheckInfo.builder()
            .checkStatus(CheckStatusEnum.NOPASS)
            .build();
    private CheckInfo waiting =CheckInfo.builder()
            .checkStatus(CheckStatusEnum.WAIT)
            .build();
    private String feedback="行";
}
