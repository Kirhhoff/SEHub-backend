package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.*;
import com.scut.se.sehubbackend.dao.member.DepartmentRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.*;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import com.scut.se.sehubbackend.enumeration.TicketType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

/**
 * 对{@link ActivityApplicationService#create(ActivityApplicationDTO)}的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class ActivityApplicationServiceTestCreate {

    @Autowired ActivityApplicationService service;
    @Autowired ActivityApplicationRepository activityApplicationRepository;
    @Autowired EtiquetteApplicationRepository etiquetteApplicationRepository;
    @Autowired HostApplicationRepository hostApplicationRepository;
    @Autowired LectureTicketApplicationRepository lectureTicketApplicationRepository;
    @Autowired PosterApplicationRepository posterApplicationRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired DepartmentRepository departmentRepository;
    @MockBean MemberContextHelper mockContextHelper;


    /**
     * <p>测试根据请求数据创建活动表及其子表</p>
     * <p>子表的具体测试在{@link EtiquetteApplicationServiceTest}</p>
     */
    @Test
    @Transactional
    public void testCreate(){
        service.create(mockRequestActivityDTO);
        verifyDataAfterCreation();
    }

    @Before
    public void setUp(){
        //mock data
        activityBasicInfo= ActivityBasicInfo.builder()
                .name("革命到底演唱会")
                .location("红馆")
                .startTime(new Date())
                .endTime(new Date())
                .description("愉快吧，现代化")
                .build();
        activitySupplementaryInfo=ActivitySupplementaryInfo.builder()
                .background("1980")
                .objective("anyone")
                .organizer("Beyond")
                .hostUnit("Beyond")
                .expectedNumOfParticipants(50000)
                .note("蒙着耳朵")
                .build();
        Department department= Department.builder().departmentName(DepartmentNameEnum.Research).memberList(new ArrayList<>()).build();
        mockMember= Member.builder()
                .studentNumber(currentUserId)
                .password("123")
                .name("光度")
                .position(PositionEnum.Minister)
                .build();
        department.addMember(mockMember);
        departmentRepository.saveAndFlush(department);

        mockEtiquetteDTO=null;
        mockHostDTO= HostApplicationDTO.builder()
                .activityBasicInfo(activityBasicInfo)
                .numOfHost(2)
                .descOfJob("")
                .build();
        mockLectureTicketDTO=LectureTicketApplicationDTO.builder()
                .activityBasicInfo(activityBasicInfo)
                .numOfTicket(200)
                .ticketScore(0.5)
                .ticketType(TicketType.DeYu)
                .build();
        mockPosterDTO=PosterApplicationDTO.builder()
                .activityBasicInfo(activityBasicInfo)
                .posterSize("3A0")
                .deadline(new Date())
                .propagandaTextRequirement("要大")
                .build();
        mockRequestActivityDTO=ActivityApplicationDTO.builder()
                .activityBasicInfo(activityBasicInfo)
                .activitySupplementaryInfo(activitySupplementaryInfo)
                .etiquetteApplication(mockEtiquetteDTO)
                .hostApplication(mockHostDTO)
                .lectureTicketApplication(mockLectureTicketDTO)
                .posterApplication(mockPosterDTO)
                .build();

        //setup 用户上下文和数据库
        memberRepository.save(mockMember);
        doReturn(mockMember).when(mockContextHelper).getCurrentPrincipal();

    }
    private void verifyDataAfterCreation() {
        //verify 其他表已经正确插入
        List<EtiquetteApplication> allEtiquette=etiquetteApplicationRepository.findAll();
        assertEquals(0,allEtiquette.size());

        List<HostApplication> allHost=hostApplicationRepository.findAll();
        assertEquals(1,allHost.size());
        actualHost=allHost.get(0);

        List<LectureTicketApplication> allLecture=lectureTicketApplicationRepository.findAll();
        assertEquals(1,allLecture.size());
        actualLecture=allLecture.get(0);

        List<PosterApplication> allPoster=posterApplicationRepository.findAll();
        assertEquals(1,allPoster.size());
        actualPoster=allPoster.get(0);

        //verify 活动
        List<ActivityApplication> allActivity=activityApplicationRepository.findAll();
        assertEquals(1,allActivity.size());
        actualActivity=allActivity.get(0);

        //verify 外键关联
        assertNull(actualActivity.getEtiquetteApplication());
        assertEquals(
                actualHost,
                actualActivity.getHostApplication()
        );
        assertEquals(
                actualLecture,
                actualActivity.getLectureTicketApplication()
        );
        assertEquals(
                actualPoster,
                actualActivity.getPosterApplication()
        );

        //verify 其他field
        assertEquals(
                mockRequestActivityDTO.getActivityBasicInfo(),
                actualActivity.getActivityBasicInfo()
        );
        assertEquals(
                mockRequestActivityDTO.getActivitySupplementaryInfo(),
                actualActivity.getActivitySupplementaryInfo()
        );

        //verify 发起人和修改人外键正确
        assertEquals(mockMember,actualActivity.getCheckInfo().getInitializer());
        assertEquals(mockMember,actualActivity.getCheckInfo().getLastModifier());
        assertEquals(mockMember,actualHost.getCheckInfo().getInitializer());
        assertEquals(mockMember,actualHost.getCheckInfo().getLastModifier());
        assertEquals(mockMember,actualLecture.getCheckInfo().getInitializer());
        assertEquals(mockMember,actualLecture.getCheckInfo().getLastModifier());
        assertEquals(mockMember,actualPoster.getCheckInfo().getInitializer());
        assertEquals(mockMember,actualPoster.getCheckInfo().getLastModifier());
    }
    private Long currentUserId=201730683314L;
    private Member mockMember;

    private ActivityBasicInfo activityBasicInfo;
    private ActivitySupplementaryInfo activitySupplementaryInfo;
    private ActivityApplicationDTO mockRequestActivityDTO;
    private EtiquetteApplicationDTO mockEtiquetteDTO;
    private HostApplicationDTO mockHostDTO;
    private LectureTicketApplicationDTO mockLectureTicketDTO;
    private PosterApplicationDTO mockPosterDTO;

    private ActivityApplication actualActivity;
    private EtiquetteApplication actualEtiquette;
    private HostApplication actualHost;
    private LectureTicketApplication actualLecture;
    private PosterApplication actualPoster;
}
