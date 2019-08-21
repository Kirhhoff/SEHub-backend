package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ApplicationNoticeDTO;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    final DTOUtil dtoUtil;
    final ContextHelper<Member> contextHelper;
    final ActivityApplicationService activityApplicationService;
    final EtiquetteApplicationService etiquetteApplicationService;
    final HostApplicationService hostApplicationService;
    final LectureTicketApplicationService lectureTicketApplicationService;
    final PosterApplicationService posterApplicationService;

    public NoticeService(ActivityApplicationService activityApplicationService, ContextHelper<Member> contextHelper, EtiquetteApplicationService etiquetteApplicationService, HostApplicationService hostApplicationService, LectureTicketApplicationService lectureTicketApplicationService, PosterApplicationService posterApplicationService, DTOUtil dtoUtil) {
        this.activityApplicationService = activityApplicationService;
        this.contextHelper = contextHelper;
        this.etiquetteApplicationService = etiquetteApplicationService;
        this.hostApplicationService = hostApplicationService;
        this.lectureTicketApplicationService = lectureTicketApplicationService;
        this.posterApplicationService = posterApplicationService;
        this.dtoUtil = dtoUtil;
    }


    ApplicationNoticeDTO get(){
        DepartmentNameEnum departmentName=contextHelper.getCurrentPrincipal().getDepartment().getDepartmentName();
        ApplicationNoticeDTO noticeDTO=ApplicationNoticeDTO.builder().build();
        switch (departmentName){
            case Relation: setRelationNotice(noticeDTO);
            case Secretary: setSecretaryNotice(noticeDTO);
            case Research: setResearchNotice(noticeDTO);
            case Propaganda: setPropagandaNotice(noticeDTO);
        }
        return noticeDTO;
    }

    private void setRelationNotice(ApplicationNoticeDTO applicationNoticeDTO){
        applicationNoticeDTO.setActivityList(dtoUtil.toDTO(
                activityApplicationService.findAll()
        ));
    }

    void setSecretaryNotice(ApplicationNoticeDTO applicationNoticeDTO){
        List<>
    }

    void setResearchNotice(ApplicationNoticeDTO applicationNoticeDTO){

    }
    void setPropagandaNotice(ApplicationNoticeDTO applicationNoticeDTO){

    }

}
