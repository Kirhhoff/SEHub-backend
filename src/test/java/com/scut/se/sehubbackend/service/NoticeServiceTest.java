package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.EtiquetteApplicationRepository;
import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.domain.activity.CheckInfo;
import com.scut.se.sehubbackend.domain.activity.EtiquetteApplication;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.dto.CheckInfoDTO;
import com.scut.se.sehubbackend.dto.MemberDTO;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.utils.DTOUtil;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

/**
 * 活动通知测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class NoticeServiceTest {

    @MockBean MemberContextHelper mockContextHelper;
    @Autowired NoticeService noticeService;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired EtiquetteApplicationRepository etiquetteApplicationRepository;
    @SpyBean DTOUtil dtoUtil;

    /**
     * <p>通过测试外联部礼仪申请通知的获取来测试通知功能</p>
     * <p>在数据库中mock一张礼仪申请表，检查实际返回与预期返回的通知是否相同</p>
     */
    @Test
    @WithMockUser(authorities = {"Etiquette","ROLE_Relation"})
    @Transactional
    public void testGetApplicationNotices() {
        assertEquals(
            expectedApplicationNotices,
            noticeService.getApplicationNotices()
        );
    }

    @Before
    public void setUp() {
        setUpDatabase();
        setUpExpectedResult();

        //mock 当前用户是外联部成员
        doReturn(relationDepartmentChecker).when(mockContextHelper).getCurrentPrincipal();

        //部分mock DTOUtil的功能
        doReturn(checkInfoDTO).when(dtoUtil).toDTO(checkInfo);
    }

    private void setUpDatabase(){
        relationDepartmentChecker = Member.builder().studentNumber(201830662011L).password("123").name("光度").position(PositionEnum.Minister).build();
        checkInfo=CheckInfo.builder()
                .submissionDate(submissionDate)
                .checkStatus(checkStatus)
                .checkDate(checkDate)
                .checkFeedback(checkFeedback)
                .initializer(requester)
                .lastModifier(relationDepartmentChecker)
                .build();
        mockEtiquette= EtiquetteApplication.builder()
                .activityBasicInfo(activityBasicInfo)
                .checkInfo(checkInfo)
                .numOfEtiquette(6)
                .rehearsalTime(new Date())
                .rehearsalSite("地球")
                .descOfJob("巴巴罗亚婆婆的语言")
                .build();
        Department otherDepartment=Department.builder().departmentName(DepartmentNameEnum.Research).memberList(new ArrayList<>()).build();
        mockRelationDepartment.addMember(relationDepartmentChecker);
        otherDepartment.addMember(requester);
        departmentRepository.save(mockRelationDepartment);
        departmentRepository.saveAndFlush(otherDepartment);
        etiquetteApplicationRepository.save(mockEtiquette);
    }
    private void setUpExpectedResult(){
        checkInfoDTO=CheckInfoDTO.builder()
                .submissionDate(submissionDate)
                .checkDate(checkDate)
                .checkStatus(checkStatus)
                .checkFeedback(checkFeedback)
                .initializer(MemberDTO.builder().studentNumber(201730683314L).build())
                .lastModifier(MemberDTO.builder().studentNumber(201830662011L).build())
                .build();
        ApplicationNotice etiquetteNotice=ApplicationNotice.builder()
                .id(mockEtiquette.getId())
                .type("etiquette")
                .activityBasicInfo(activityBasicInfo)
                .checkInfoDTO(checkInfoDTO)
                .build();
        expectedApplicationNotices.add(etiquetteNotice);
    }
    EtiquetteApplication mockEtiquette;

    private Department mockRelationDepartment = Department.builder()
            .departmentName(DepartmentNameEnum.Relation)
            .memberList(new ArrayList<>())
            .build();
    private Member requester= Member.builder().studentNumber(201730683314L).password("123").name("光度").position(PositionEnum.Minister).build();
    private Member relationDepartmentChecker;
    private ActivityBasicInfo activityBasicInfo=ActivityBasicInfo.builder().name("崩星爆裂拳").build();
    private Date submissionDate=new Date();
    private Date checkDate=new Date();
    private CheckStatusEnum checkStatus=CheckStatusEnum.NOPASS;
    private String checkFeedback="夭寿啦！！";
    private CheckInfo checkInfo;
    private CheckInfoDTO checkInfoDTO;
    private List<ApplicationNotice> expectedApplicationNotices=new ArrayList<>();
}