package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.*;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.enumeration.PositionEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

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
    public void testToDTOForActivityApplication() {
        assertEquals(
                expectedConvertedActivityApplicationDTO,
                dtoUtil.toDTO(activityApplicationToConvert)
        );
    }

    DepartmentDTO expectedDepartmentDTO;
    Department departmentToConvert;

    /**
     * <p>测试Department转化来同时测试Member和Department的转化</p>
     * <p>虽然逻辑简单，但后续出问题很麻烦，因此还是进行简单的测试</p>
     */
    @Test
    public void testToDTOForDepartment() {
        prepareDepartmentData();
        assertEquals(
                expectedDepartmentDTO,
                dtoUtil.toDTO(departmentToConvert)
        );
    }

    @Before
    public void prepareApplicationData(){
        prepareDO();
        prepareDTO();
    }

    private void prepareDepartmentData(){
        //prepare DO
        Member memberWithNoAuthority=initializer;

        Member memberWithTwoAuthorities=lastModifier;
        Authority authority1= Authority.builder().authorityName("Where am I???").build();
        Authority authority2= Authority.builder().authorityName("Let me go!").build();
        memberWithTwoAuthorities.addAuthority(authority1);
        memberWithTwoAuthorities.addAuthority(authority2);

        departmentToConvert= Department.builder()
                .departmentName(DepartmentNameEnum.Research)
                .memberList(new ArrayList<>())
                .departmentDescription("Not bad!")
                .build();

        departmentToConvert.addMember(memberWithNoAuthority);
        departmentToConvert.addMember(memberWithTwoAuthorities);


        //prepare corresponding DTO
        String authorityString1="Where am I???";
        String authorityString2="Let me go!";
        List<String> authorityStrings=new ArrayList<>();
        authorityStrings.add(authorityString1);
        authorityStrings.add(authorityString2);

        MemberDTO memberWithNoAuthorityDTO= MemberDTO.builder()
                .studentNumber(memberWithNoAuthority.getStudentNumber())
                .name(memberWithNoAuthority.getName())
                .position(memberWithNoAuthority.getPosition())
                .phoneNumber(memberWithNoAuthority.getPhoneNumber())
                .departmentName(departmentToConvert.getDepartmentName())
                .authorityList(new ArrayList<>())
                .build();

        MemberDTO memberWithTwoAuthoritiesDTO= MemberDTO.builder()
                .studentNumber(memberWithTwoAuthorities.getStudentNumber())
                .name(memberWithTwoAuthorities.getName())
                .position(memberWithTwoAuthorities.getPosition())
                .phoneNumber(memberWithTwoAuthorities.getPhoneNumber())
                .departmentName(departmentToConvert.getDepartmentName())
                .authorityList(authorityStrings)
                .build();
        List<MemberDTO> memberDTOs=new ArrayList<>();
        memberDTOs.add(memberWithNoAuthorityDTO);
        memberDTOs.add(memberWithTwoAuthoritiesDTO);
        expectedDepartmentDTO= DepartmentDTO.builder()
                .departmentName(departmentToConvert.getDepartmentName())
                .memberList(memberDTOs)
                .departmentDescription(departmentToConvert.getDepartmentDescription())
                .build();
    }

    public void prepareDO(){
        etiquetteActivityBasicInfo = activityBasicInfo;
        hostActivityBasicInfo = activityBasicInfo;
        lectureTicketActivityBasicInfo = activityBasicInfo;
        posterActivityBasicInfo = activityBasicInfo;

        EtiquetteApplication etiquetteApplication=EtiquetteApplication.builder()
                .id(etiquetteId)
                .activityBasicInfo(etiquetteActivityBasicInfo)
                .numOfEtiquette(numOfEtiquette)
                .rehearsalTime(etiquetteRehearsalTime)
                .rehearsalSite(etiquetteRehearsalSite)
                .descOfJob(descOfEtiquetteJob)
                .build();

        HostApplication hostApplication=HostApplication.builder()
                .id(hostId)
                .activityBasicInfo(hostActivityBasicInfo)
                .numOfHost(numOfHost)
                .descOfJob(descOfHostJob)
                .build();

        LectureTicketApplication lectureTicketApplication=LectureTicketApplication.builder()
                .id(lectureTicketId)
                .activityBasicInfo(lectureTicketActivityBasicInfo)
                .numOfTicket(numOfTicket)
                .build();


        PosterApplication posterApplication=PosterApplication.builder()
                .id(posterId)
                .activityBasicInfo(posterActivityBasicInfo)
                .deadline(posterDeadline)
                .propagandaTextRequirement(propagandaTextRequirement)
                .posterSize(posterSize)
                .build();

        activityApplicationToConvert =ActivityApplication.builder()
                .id(activityId)
                .activityBasicInfo(activityBasicInfo)
                .activitySupplementaryInfo(activitySupplementaryInfo)
                .checkInfo(
                        CheckInfo.builder()
                                .checkStatus(checkStatus)
                                .checkFeedback(checkFeedback)
                                .submissionDate(submissionDate)
                                .checkDate(checkDate)
                                .initializer(initializer)
                                .lastModifier(lastModifier)
                                .build()
                )
                .build();

        activityApplicationToConvert.setEtiquetteApplication(etiquetteApplication);
        activityApplicationToConvert.setHostApplication(hostApplication);
        activityApplicationToConvert.setLectureTicketApplication(lectureTicketApplication);
        activityApplicationToConvert.setPosterApplication(posterApplication);
    }
    public void prepareDTO(){
        EtiquetteApplicationDTO etiquetteApplicationDTO=EtiquetteApplicationDTO.builder()
                .id(etiquetteId)
                .activityBasicInfo(etiquetteActivityBasicInfo)
                .numOfEtiquette(numOfEtiquette)
                .rehearsalTime(etiquetteRehearsalTime)
                .rehearsalSite(etiquetteRehearsalSite)
                .descOfJob(descOfEtiquetteJob)
                .relatedActivity(activityId)
                .build();

        HostApplicationDTO hostApplicationDTO=HostApplicationDTO.builder()
                .id(hostId)
                .activityBasicInfo(hostActivityBasicInfo)
                .numOfHost(numOfHost)
                .descOfJob(descOfHostJob)
                .relatedActivity(activityId)
                .build();

        LectureTicketApplicationDTO lectureTicketApplicationDTO= LectureTicketApplicationDTO.builder()
                .id(lectureTicketId)
                .activityBasicInfo(lectureTicketActivityBasicInfo)
                .numOfTicket(numOfTicket)
                .relatedActivity(activityId)
                .build();

        PosterApplicationDTO posterApplicationDTO= PosterApplicationDTO.builder()
                .id(posterId)
                .activityBasicInfo(posterActivityBasicInfo)
                .deadline(posterDeadline)
                .propagandaTextRequirement(propagandaTextRequirement)
                .posterSize(posterSize)
                .relatedActivity(activityId)
                .build();

        expectedConvertedActivityApplicationDTO= ActivityApplicationDTO.builder()
                .id(activityId)
                .activityBasicInfo(activityBasicInfo)
                .activitySupplementaryInfo(activitySupplementaryInfo)
                .etiquetteApplication(etiquetteApplicationDTO)
                .hostApplication(hostApplicationDTO)
                .lectureTicketApplication(lectureTicketApplicationDTO)
                .posterApplication(posterApplicationDTO)
                .checkInfoDTO(
                        CheckInfoDTO.builder()
                                .checkStatus(checkStatus)
                                .checkFeedback(checkFeedback)
                                .submissionDate(submissionDate)
                                .checkDate(checkDate)
                                .initializer(initializerDTO)
                                .lastModifier(lastModifierDTO)
                                .build()
                )
                .build();
    }

    private Long etiquetteId=991025L;
    private ActivityBasicInfo etiquetteActivityBasicInfo;
    private Integer numOfEtiquette=6;
    private Date etiquetteRehearsalTime=new Date();
    private String etiquetteRehearsalSite="山达尔星";
    private String descOfEtiquetteJob="";

    private Long hostId=990226L;
    private ActivityBasicInfo hostActivityBasicInfo;
    private Integer numOfHost=1;
    private String descOfHostJob="客观地描述,what you have seen，用你的大鼻音";

    private Long lectureTicketId=991116L;
    private ActivityBasicInfo lectureTicketActivityBasicInfo;
    private Integer numOfTicket=300;

    private Long posterId=991874L;
    private ActivityBasicInfo posterActivityBasicInfo;
    private Date posterDeadline=new Date();
    private String propagandaTextRequirement="字要大，要能够彰显软件学院的雄厚财力";
    private String posterSize="3A0";

    private Long activityId=991026L;
    private ActivityBasicInfo activityBasicInfo = ActivityBasicInfo.builder()
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
    private MemberDTO initializerDTO= MemberDTO.builder()
            .studentNumber(201730683314L)
            .name("光度")
            .position(PositionEnum.Minister)
            .phoneNumber("15564278737")
            .departmentName(null)
            .authorityList(new ArrayList<>())
            .build();
    private MemberDTO lastModifierDTO= MemberDTO.builder()
            .studentNumber(201830662011L)
            .name("彭天祥")
            .position(PositionEnum.Staff)
            .phoneNumber("15521093401")
            .departmentName(null)
            .authorityList(new ArrayList<>())
            .build();
}