package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.Application;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import com.scut.se.sehubbackend.utils.ContextHelper;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知相关的业务
 */
@Service
public class NoticeService {

    private final DTOUtil dtoUtil;
    private final ContextHelper<Member> contextHelper;
    private final ActivityApplicationService activityApplicationService;
    private final EtiquetteApplicationService etiquetteApplicationService;
    private final HostApplicationService hostApplicationService;
    private final LectureTicketApplicationService lectureTicketApplicationService;
    private final PosterApplicationService posterApplicationService;

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

        //部门不能为空
        if(contextHelper.getCurrentPrincipal().getDepartment()==null)
            return new ArrayList<>();

        //根据部门名获取相应表的通知
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
