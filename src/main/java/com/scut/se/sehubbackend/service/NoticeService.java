package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.Application;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知相关的业务
 */
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


    /**
     * 获取当前部门所有的活动通知，以一个{@link ApplicationNotice}数组的形式返回
     * @return 申请表通知的数组
     * @see ApplicationNotice
     */
    public List<ApplicationNotice> getApplicationNotices(){
        DepartmentNameEnum departmentName=contextHelper.getCurrentPrincipal().getDepartment().getDepartmentName();
        List<? extends Application> applicationList=null;
        switch (departmentName){
            case StandingCommittee: applicationList=activityApplicationService.findAll();
                break;
            case Relation: applicationList=etiquetteApplicationService.findAll();
                break;
            case Secretary: applicationList=hostApplicationService.findAll();
                break;
            case Research: applicationList=lectureTicketApplicationService.findAll();
                break;
            case Propaganda: applicationList=posterApplicationService.findAll();
        }
        return dtoUtil.toApplicationNotice(applicationList);
    }

}
