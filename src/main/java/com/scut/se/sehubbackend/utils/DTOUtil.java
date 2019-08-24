package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.Application;
import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.*;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOUtil {

    public List<ActivityApplicationDTO> toDTO(List<ActivityApplication> activityApplications){
        if (activityApplications==null)
            return null;
        else {
            List<ActivityApplicationDTO> activityApplicationDTOs=new ArrayList<>();
            for(ActivityApplication activityApplication:activityApplications)
                activityApplicationDTOs.add(toDTO(activityApplication));
            return activityApplicationDTOs;
        }
    }

    public ActivityApplicationDTO toDTO(ActivityApplication activityApplication){
        return activityApplication==null
                ?null
                : ActivityApplicationDTO.builder()
                    .id(activityApplication.getId())
                    .activityBasicInfo(activityApplication.getActivityBasicInfo())
                    .activitySupplementaryInfo(activityApplication.getActivitySupplementaryInfo())
                    .etiquetteApplication(toDTO(activityApplication.getEtiquetteApplication()))
                    .hostApplication(toDTO(activityApplication.getHostApplication()))
                    .lectureTicketApplication(toDTO(activityApplication.getLectureTicketApplication()))
                    .posterApplication(toDTO(activityApplication.getPosterApplication()))
                    .checkInfoDTO(toDTO(activityApplication.getCheckInfo()))
                    .build();
    }

    public CheckInfoDTO toDTO(CheckInfo checkInfo){
        return checkInfo==null
                ?null
                : CheckInfoDTO.builder()
                    .checkStatus(checkInfo.getCheckStatus())
                    .checkFeedback(checkInfo.getCheckFeedback())
                    .submissionDate(checkInfo.getSubmissionDate())
                    .checkDate(checkInfo.getCheckDate())
                    .initializer(toDTO(checkInfo.getInitializer()))
                    .lastModifier(toDTO(checkInfo.getLastModifier()))
                    .build();
    }

    public EtiquetteApplicationDTO toDTO(EtiquetteApplication etiquetteApplication){
        return etiquetteApplication==null
                ?null
                : EtiquetteApplicationDTO.builder()
                    .id(etiquetteApplication.getId())
                    .activityBasicInfo(etiquetteApplication.getActivityBasicInfo())
                    .numOfEtiquette(etiquetteApplication.getNumOfEtiquette())
                    .rehearsalTime(etiquetteApplication.getRehearsalTime())
                    .rehearsalSite(etiquetteApplication.getRehearsalSite())
                    .descOfJob(etiquetteApplication.getDescOfJob())
                    .relatedActivity(
                            etiquetteApplication.getActivityThisBelongsTo()==null
                                    ?null
                                    :etiquetteApplication.getActivityThisBelongsTo().getId())
                    .build();
    }

    public HostApplicationDTO toDTO(HostApplication hostApplication){
        return hostApplication==null
                ?null
                : HostApplicationDTO.builder()
                    .id(hostApplication.getId())
                    .activityBasicInfo(hostApplication.getActivityBasicInfo())
                    .numOfHost(hostApplication.getNumOfHost())
                    .descOfJob(hostApplication.getDescOfJob())
                    .relatedActivity(
                        hostApplication.getActivityThisBelongsTo()==null
                                ?null
                                :hostApplication.getActivityThisBelongsTo().getId())
                    .build();
    }

    public LectureTicketApplicationDTO toDTO(LectureTicketApplication lectureTicketApplication){
        return lectureTicketApplication==null
                ?null
                : LectureTicketApplicationDTO.builder()
                    .id(lectureTicketApplication.getId())
                    .activityBasicInfo(lectureTicketApplication.getActivityBasicInfo())
                    .numOfTicket(lectureTicketApplication.getNumOfTicket())
                .ticketScore(lectureTicketApplication.getTicketScore())
                .ticketType(lectureTicketApplication.getTicketType())
                .relatedActivity(
                        lectureTicketApplication.getActivityThisBelongsTo()==null
                                ?null
                                :lectureTicketApplication.getActivityThisBelongsTo().getId())
                    .build();
    }

    public PosterApplicationDTO toDTO(PosterApplication posterApplication){
        return posterApplication==null
                ?null
                : PosterApplicationDTO.builder()
                    .id(posterApplication.getId())
                    .activityBasicInfo(posterApplication.getActivityBasicInfo())
                    .deadline(posterApplication.getDeadline())
                    .propagandaTextRequirement(posterApplication.getPropagandaTextRequirement())
                    .posterSize(posterApplication.getPosterSize())
                    .relatedActivity(
                        posterApplication.getActivityThisBelongsTo()==null
                                ?null
                                :posterApplication.getActivityThisBelongsTo().getId())
                    .build();
    }

    public MemberDTO toDTO(Member member){
        if (member==null)
            return null;
        else {
            List<String> authorityList=new ArrayList<>();
            for (Authority authority:member.getAuthorityList())
                authorityList.add(authority.getAuthorityName());

            DepartmentNameEnum departmentName= member.getDepartment()==null
                    ?null
                    :member.getDepartment().getDepartmentName();

            return MemberDTO.builder()
                    .studentNumber(member.getStudentNumber())
                    .name(member.getName())
                    .position(member.getPosition())
                    .phoneNumber(member.getPhoneNumber())
                    .email(member.getEmail())
                    .departmentName(departmentName)
                    .authorityList(authorityList)
                    .build();
        }
    }

    public DepartmentDTO toDTO(Department department){
        if (department==null)
            return null;
        else {
            List<MemberDTO> memberList=new ArrayList<>();
            for(Member member:department.getMemberList())
                memberList.add(toDTO(member));

            return DepartmentDTO.builder()
                    .departmentName(department.getDepartmentName())
                    .memberList(memberList)
                    .departmentDescription(department.getDepartmentDescription())
                    .build();
        }
    }


    public List<ApplicationNotice> toApplicationNotice(List<? extends Application> applications){
        if (applications==null)
            return null;
        else {
            List<ApplicationNotice> applicationNotices=new ArrayList<>();
            for (Application application:applications)
                applicationNotices.add(toApplicationNotice(application));
            return applicationNotices;
        }
    }

    public ApplicationNotice toApplicationNotice(Application application ){
        return application==null
                ?null
                :ApplicationNotice.builder()
                .id(application.getId())
                .type(toType(application))
                .activityBasicInfo(application.getActivityBasicInfo())
                .checkInfoDTO(toDTO(application.getCheckInfo()))
                .build();
    }

    /**
     * <p>将申请表的类名转化为type字段</p>
     * <p>主要是去掉Application后缀同时首字母小写，如：</p>
     * <ul>
     *     <li>"EtiquetteApplication" => "etiquette"</li>
     *     <li>"LectureTicketApplication" => "lectureTicket"</li>
     * @param application 申请表
     * @return 转化后的type字段
     */
    private String toType(Application application){
        //获取不带包的类名
        String typeName=application.getClass().getSimpleName();
        //去掉Application后缀
        String eliminateApplication=typeName.replace("Application","");
        //首字母小写并返回
        return eliminateApplication.substring(0,1).toLowerCase()+eliminateApplication.substring(1);
    }
}
