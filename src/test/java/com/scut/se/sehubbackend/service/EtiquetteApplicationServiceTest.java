package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.domain.activity.CheckInfo;
import com.scut.se.sehubbackend.domain.activity.EtiquetteApplication;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class EtiquetteApplicationServiceTest {

    @MockBean MemberContextHelper mockContextHelper;
    @Autowired EtiquetteApplicationService etiquetteApplicationService;
    @Autowired MemberRepository memberRepository;

    /**
     * <p>测试创建不与活动关联的礼仪申请表</p>
     * <p>模拟调用而后验证数据库中数据的正确性</p>
     */
    @Test
    @Transactional
    public void testCreateSingle() {

        //模拟创建
        etiquetteApplicationService.create(mockRequestDTO);

        verifyDataCreation();
    }

    @Before
    public void before(){
        setUpData();

        //设置用户上下文
        doReturn(mockMember).when(mockContextHelper).getCurrentPrincipal();
    }

    private void setUpData(){
        timestampBeforeCreation=new Date();
        mockMember= Member.builder()
                .studentNumber(201730683314L)
                .password("password")
                .name("Luminosity")
                .position(PositionEnum.Minister)
                .authorityList(new ArrayList<>())
                .department(null)
                .build();
        mockRequestDTO= EtiquetteApplicationDTO.builder()
                .activityBasicInfo(
                        ActivityBasicInfo.builder()
                                .name("野野口修的自述")
                                .location("早稻田国立第一医院")
                                .startTime(new Date())
                                .endTime(new Date())
                                .description("终章")
                                .build()
                )
                .numOfEtiquette(5)
                .rehearsalTime(new Date())
                .rehearsalSite("光度场")
                .descOfJob("学习独立过程")
                .build();
        memberRepository.save(mockMember);
    }

    private void verifyDataCreation(){
        List<EtiquetteApplication> allData=etiquetteApplicationService.findAll();
        assertEquals(allData.size(),1);

        EtiquetteApplication expectedApplication=allData.get(0);
        assertEquals(
                mockRequestDTO.getActivityBasicInfo(),
                expectedApplication.getActivityBasicInfo()
        );

        assertEquals(
                mockRequestDTO.getNumOfEtiquette(),
                expectedApplication.getNumOfEtiquette()
        );

        assertEquals(
                mockRequestDTO.getRehearsalTime(),
                expectedApplication.getRehearsalTime()
        );

        assertEquals(
                mockRequestDTO.getRehearsalSite(),
                expectedApplication.getRehearsalSite()
        );

        assertEquals(
                mockRequestDTO.getDescOfJob(),
                expectedApplication.getDescOfJob()
        );

        CheckInfo expectedCheckInfo=expectedApplication.getCheckInfo();
        assertEquals(
                CheckStatusEnum.WAIT,
                expectedCheckInfo.getCheckStatus()
        );

        assertNull(expectedCheckInfo.getCheckFeedback());
        assertNull(expectedCheckInfo.getCheckDate());
        assertEquals(
                mockMember,
                expectedCheckInfo.getInitializer()
        );
        assertEquals(
                mockMember,
                expectedCheckInfo.getLastModifier()
        );

        assertTrue(timestampBeforeCreation.before(expectedCheckInfo.getSubmissionDate()));
        assertTrue(new Date().after(expectedCheckInfo.getSubmissionDate()));

        assertNull(expectedApplication.getActivityThisBelongsTo());
    }
    private Member mockMember;
    private EtiquetteApplicationDTO mockRequestDTO;
    private Date timestampBeforeCreation;
}