package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.dto.*;
import org.springframework.stereotype.Component;

@Component
public class DTOUtil {

    ActivityApplicationDTO toDTO(ActivityApplication activityApplication){
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

    EtiquetteApplicationDTO toDTO(EtiquetteApplication etiquetteApplication){
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

    HostApplicationDTO toDTO(HostApplication hostApplication){
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

    LectureTicketApplicationDTO toDTO(LectureTicketApplication lectureTicketApplication){
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

    PosterApplicationDTO toDTO(PosterApplication posterApplication){
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
}
