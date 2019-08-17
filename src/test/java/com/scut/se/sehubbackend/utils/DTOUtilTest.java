package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.*;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class DTOUtilTest {

    DTOUtil dtoUtil=new DTOUtil();
    ActivityApplication activityApplicationToConvert;
    ActivityApplicationDTO expectedConvertedActivityApplicationDTO;

    /**
     * <p>因为Util类较简单，对申请表DTO的测试仅测试活动申请表的转换，捎带测试其他转换方法</p>
     * <p>测试很简单，构造一个活动申请的数据库对象，再构造结果的DTO，verify实际结果与构造结果一致</p>
     */
    @Test
    public void testToDTOForTotalActivityApplication() {
        assertEquals(
                expectedConvertedActivityApplicationDTO,
                dtoUtil.toDTO(activityApplicationToConvert)
        );
    }

    @Before
    public void before(){
        prepareDO();
        prepareDTO();
    }

    public void prepareDO(){
        etiquetteActivityMainInfo=activityMainInfo;
        hostActivityMainInfo=activityMainInfo;
        lectureTicketActivityMainInfo=activityMainInfo;
        posterActivityMainInfo=activityMainInfo;

        EtiquetteApplication etiquetteApplication=EtiquetteApplication.builder()
                .id(etiquetteId)
                .activityMainInfo(etiquetteActivityMainInfo)
                .numOfEtiquette(numOfEtiquette)
                .rehearsalTime(etiquetteRehearsalTime)
                .rehearsalSite(etiquetteRehearsalSite)
                .descOfJob(descOfEtiquetteJob)
                .build();

        HostApplication hostApplication=HostApplication.builder()
                .id(hostId)
                .activityMainInfo(hostActivityMainInfo)
                .numOfHost(numOfHost)
                .descOfJob(descOfHostJob)
                .build();

        LectureTicketApplication lectureTicketApplication=LectureTicketApplication.builder()
                .id(lectureTicketId)
                .activityMainInfo(lectureTicketActivityMainInfo)
                .numOfTicket(numOfTicket)
                .build();


        PosterApplication posterApplication=PosterApplication.builder()
                .id(posterId)
                .activityMainInfo(posterActivityMainInfo)
                .deadline(posterDeadline)
                .propagandaTextRequirement(propagandaTextRequirement)
                .posterSize(posterSize)
                .build();

        activityApplicationToConvert =ActivityApplication.builder()
                .id(activityId)
                .activityMainInfo(activityMainInfo)
                .activitySupplementaryInfo(activitySupplementaryInfo)
                .checkStatus(checkStatus)
                .checkFeedback(checkFeedback)
                .submissionDate(submissionDate)
                .checkDate(checkDate)
                .initializer(initializer)
                .lastModifier(lastModifier)
                .build();

        activityApplicationToConvert.setEtiquetteApplication(etiquetteApplication);
        activityApplicationToConvert.setHostApplication(hostApplication);
        activityApplicationToConvert.setLectureTicketApplication(lectureTicketApplication);
        activityApplicationToConvert.setPosterApplication(posterApplication);
    }
    public void prepareDTO(){
        EtiquetteApplicationDTO etiquetteApplicationDTO=EtiquetteApplicationDTO.builder()
                .id(etiquetteId)
                .activityMainInfo(etiquetteActivityMainInfo)
                .numOfEtiquette(numOfEtiquette)
                .rehearsalTime(etiquetteRehearsalTime)
                .rehearsalSite(etiquetteRehearsalSite)
                .descOfJob(descOfEtiquetteJob)
                .type(EtiquetteApplicationDTO.TYPE)
                .hasRelatedActivityApplication(true)
                .build();

        HostApplicationDTO hostApplicationDTO=HostApplicationDTO.builder()
                .id(hostId)
                .activityMainInfo(hostActivityMainInfo)
                .numOfHost(numOfHost)
                .descOfJob(descOfHostJob)
                .type(HostApplicationDTO.TYPE)
                .hasRelatedActivityApplication(true)
                .build();

        LectureTicketApplicationDTO lectureTicketApplicationDTO= LectureTicketApplicationDTO.builder()
                .id(lectureTicketId)
                .activityMainInfo(lectureTicketActivityMainInfo)
                .numOfTicket(numOfTicket)
                .type(LectureTicketApplicationDTO.TYPE)
                .hasRelatedActivityApplication(true)
                .build();

        PosterApplicationDTO posterApplicationDTO= PosterApplicationDTO.builder()
                .id(posterId)
                .activityMainInfo(posterActivityMainInfo)
                .deadline(posterDeadline)
                .propagandaTextRequirement(propagandaTextRequirement)
                .posterSize(posterSize)
                .type(PosterApplicationDTO.TYPE)
                .hasRelatedActivityApplication(true)
                .build();

        expectedConvertedActivityApplicationDTO= ActivityApplicationDTO.builder()
                .id(activityId)
                .activityMainInfo(activityMainInfo)
                .activitySupplementaryInfo(activitySupplementaryInfo)
                    .subApplication(etiquetteApplicationDTO)
                    .subApplication(hostApplicationDTO)
                    .subApplication(lectureTicketApplicationDTO)
                    .subApplication(posterApplicationDTO)
                .checkStatus(checkStatus)
                .checkFeedback(checkFeedback)
                .submissionDate(submissionDate)
                .checkDate(checkDate)
                .initializer(initializer)
                .lastModifier(lastModifier)
                .build();
    }

    private Long etiquetteId=991025L;
    private ActivityMainInfo etiquetteActivityMainInfo;
    private Integer numOfEtiquette=6;
    private Date etiquetteRehearsalTime=new Date();
    private String etiquetteRehearsalSite="山达尔星";
    private String descOfEtiquetteJob="";

    private Long hostId=990226L;
    private ActivityMainInfo hostActivityMainInfo;
    private Integer numOfHost=1;
    private String descOfHostJob="客观地描述,what you have seen，用你的大鼻音";

    private Long lectureTicketId=991116L;
    private ActivityMainInfo lectureTicketActivityMainInfo;
    private Integer numOfTicket=300;

    private Long posterId=991874L;
    private ActivityMainInfo posterActivityMainInfo;
    private Date posterDeadline=new Date();
    private String propagandaTextRequirement="字要大，要能够彰显软件学院的雄厚财力";
    private String posterSize="3A0";

    private Long activityId=991026L;
    private ActivityMainInfo activityMainInfo= ActivityMainInfo.builder()
            .name("为了忘却的纪念")
            .location("纽约新复联总部")
            .startTime(new Date())
            .endTime(new Date())
            .description("圣诞节")
            .build();
    private ActivitySupplementaryInfo activitySupplementaryInfo=ActivitySupplementaryInfo.builder()
            .background("复联四结束后的第一个元旦前")
            .objective("复仇者联盟")
            .organizer("复仇者联盟")
            .hostUnit("复仇者联盟")
            .expectedNumOfParticipants(30)
            .note("为")
            .build();
    private CheckStatusEnum checkStatus=CheckStatusEnum.WAIT;
    private String checkFeedback=null;
    private Date submissionDate=new Date();
    private Date checkDate=new Date();
    private Member initializer= Member.builder()
            .studentNumber(201730683314L)
            .password("i m dying just to know your name")
            .name("光度")
            .position(PositionEnum.Minister)
            .phoneNumber("15564278737")
            .department(new Department())
            .authorityList(new ArrayList<>())
            .build();
    private Member lastModifier= Member.builder()
            .studentNumber(201830662011L)
            .password("luminosity")
            .name("彭天祥")
            .position(PositionEnum.Staff)
            .phoneNumber("15521093401")
            .department(new Department())
            .authorityList(new ArrayList<>())
            .build();
}