package com.scut.se.sehubbackend.utils;

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
                    .activityMainInfo(activityApplication.getActivityMainInfo())
                    .activitySupplementaryInfo(activityApplication.getActivitySupplementaryInfo())
                        .subApplication(toDTO(activityApplication.getEtiquetteApplication()))
                        .subApplication(toDTO(activityApplication.getHostApplication()))
                        .subApplication(toDTO(activityApplication.getLectureTicketApplication()))
                        .subApplication(toDTO(activityApplication.getPosterApplication()))
                    .checkStatus(activityApplication.getCheckStatus())
                    .checkFeedback(activityApplication.getCheckFeedback())
                    .submissionDate(activityApplication.getSubmissionDate())
                    .checkDate(activityApplication.getCheckDate())
                    .initializer(activityApplication.getInitializer())
                    .lastModifier(activityApplication.getLastModifier())
                    .build();
    }

    public EtiquetteApplicationDTO toDTO(EtiquetteApplication etiquetteApplication){
        return etiquetteApplication==null
                ?null
                : EtiquetteApplicationDTO.builder()
                    .id(etiquetteApplication.getId())
                    .activityMainInfo(etiquetteApplication.getActivityMainInfo())
                    .numOfEtiquette(etiquetteApplication.getNumOfEtiquette())
                    .rehearsalTime(etiquetteApplication.getRehearsalTime())
                    .rehearsalSite(etiquetteApplication.getRehearsalSite())
                    .descOfJob(etiquetteApplication.getDescOfJob())
                    .type(EtiquetteApplicationDTO.TYPE)
                    .hasRelatedActivityApplication(etiquetteApplication.getActivityThisBelongsTo() != null)
                    .build();
    }

    public HostApplicationDTO toDTO(HostApplication hostApplication){
        return hostApplication==null
                ?null
                : HostApplicationDTO.builder()
                    .id(hostApplication.getId())
                    .activityMainInfo(hostApplication.getActivityMainInfo())
                    .numOfHost(hostApplication.getNumOfHost())
                    .descOfJob(hostApplication.getDescOfJob())
                    .type(HostApplicationDTO.TYPE)
                    .hasRelatedActivityApplication(hostApplication.getActivityThisBelongsTo() != null)
                    .build();
    }

    public LectureTicketApplicationDTO toDTO(LectureTicketApplication lectureTicketApplication){
        return lectureTicketApplication==null
                ?null
                : LectureTicketApplicationDTO.builder()
                    .id(lectureTicketApplication.getId())
                    .activityMainInfo(lectureTicketApplication.getActivityMainInfo())
                    .numOfTicket(lectureTicketApplication.getNumOfTicket())
                    .type(LectureTicketApplicationDTO.TYPE)
                    .hasRelatedActivityApplication(lectureTicketApplication.getActivityThisBelongsTo() != null)
                    .build();
    }

    public PosterApplicationDTO toDTO(PosterApplication posterApplication){
        return posterApplication==null
                ?null
                : PosterApplicationDTO.builder()
                    .id(posterApplication.getId())
                    .activityMainInfo(posterApplication.getActivityMainInfo())
                    .deadline(posterApplication.getDeadline())
                    .propagandaTextRequirement(posterApplication.getPropagandaTextRequirement())
                    .posterSize(posterApplication.getPosterSize())
                    .type(PosterApplicationDTO.TYPE)
                    .hasRelatedActivityApplication(posterApplication.getActivityThisBelongsTo() != null)
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
                    .id(department.getId())
                    .departmentName(department.getDepartmentName())
                    .memberList(memberList)
                    .departmentDescription(department.getDepartmentDescription())
                    .build();
        }
    }
}
