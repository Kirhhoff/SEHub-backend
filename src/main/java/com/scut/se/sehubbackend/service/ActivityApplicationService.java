package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.ActivityApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.security.UserDetailsUtil;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityApplicationService {

    final UserDetailsService userDetailsService;
    final UserDetailsUtil<Member> userDetailsUtil;
    final ActivityApplicationRepository activityApplicationRepository;
    final DTOUtil dtoUtil;

    public ActivityApplicationService(UserDetailsService userDetailsService, UserDetailsUtil<Member> userDetailsUtil, ActivityApplicationRepository activityApplicationRepository, DTOUtil dtoUtil) {
        this.userDetailsService = userDetailsService;
        this.userDetailsUtil = userDetailsUtil;
        this.activityApplicationRepository = activityApplicationRepository;
        this.dtoUtil = dtoUtil;
    }

    //
//    @Autowired
//    private ActivityApplicationRepository activityApplicationRepository;
//
//    @Autowired
//    private EtiquetteApplicationService etiquetteApplicationService;
//
//    @Autowired
//    private HostApplicationService hostApplicationService;
//
//    @Autowired
//    private LectureTicketApplicationService lectureTicketApplicationService;
//
//    @Autowired
//    private PosterApplicationService posterApplicationService;
//
//    // DTO的目的是没有必要返回给前端礼仪申请等各种辅助信息，辅助信息可选展示即点击按钮再展示
//    @Transactional
//    public ActivityApplicationDTO save(ActivityApplicationDTO activityApplicationDTO) {
//        Long id = KeyUtil.genUniqueKey();
//
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        activityApplication.setId(id);
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//
//
//        return activityApplicationDTO;
//    }
//
    public List<ActivityApplicationDTO> getAllActivityApplicationOfCurrentDepartment() {
        String currentUsername= (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails currentUserDetails=userDetailsService.loadUserByUsername(currentUsername);
        Member currentMember=userDetailsUtil.from(currentUserDetails);

        return dtoUtil.toDTO(
                activityApplicationRepository.findAllByDepartment(currentMember.getDepartment())
        );
    }
//
//    public ActivityApplicationDTO findById(Long id) {
//        ActivityApplication activityApplication = activityApplicationRepository.findById(id).orElse(null);
//
//        ActivityApplicationDTO activityApplicationDTO = new ActivityApplicationDTO();
//
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//
//        return activityApplicationDTO;
//    }
//
//    public EtiquetteApplication findEtiquetteApplicationById(Long id) {
//        return etiquetteApplicationService.findById(id);
//    }
//
//    public HostApplication findHostApplicationById(Long id) {
//        return hostApplicationService.findById(id);
//    }
//
//    public LectureTicketApplication findLectureTicketApplicationById(Long id) {
//        return lectureTicketApplicationService.findById(id);
//    }
//
//    public PosterApplication findPosterApplicationById(Long id) {
//        return posterApplicationService.findById(id);
//    }
//
//    @Transactional
//    public ActivityApplicationDTO pass(ActivityApplicationDTO activityApplicationDTO) {
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        //判断审核状态
//        if (!activityApplicationDTO.getCheckStatus().equals(CheckStatusEnum.WAIT.getCode())) {
//            log.error("【审核通过】活动申请状态不正确, activityId={}, activityCheckStatus={}", activityApplicationDTO.getId(), activityApplicationDTO.getCheckStatus());
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_STATUS_ERROR);
//        }
//
//        //修改审核状态
//        activityApplicationDTO.setCheckStatus(CheckStatusEnum.PASS.getCode());
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//        ActivityApplication updateResult = activityApplicationRepository.save(activityApplication);
//        if (updateResult == null) {
//            log.error("【审核通过】更新失败, activityApplication={}", activityApplication);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_UPDATE_FAILED);
//        }
//
//        return activityApplicationDTO;
//    }
//
//    @Transactional
//    public ActivityApplicationDTO nopass(ActivityApplicationDTO activityApplicationDTO) {
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        //判断审核状态
//        if (!activityApplicationDTO.getCheckStatus().equals(CheckStatusEnum.WAIT.getCode())) {
//            log.error("【审核不通过】活动申请状态不正确, activityId={}, activityCheckStatus={}", activityApplicationDTO.getId(), activityApplicationDTO.getCheckStatus());
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_STATUS_ERROR);
//        }
//
//        //修改审核状态
//        activityApplicationDTO.setCheckStatus(CheckStatusEnum.NOPASS.getCode());
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//        ActivityApplication updateResult = activityApplicationRepository.save(activityApplication);
//        if (updateResult == null) {
//            log.error("【审核不通过】更新失败, activityApplication={}", activityApplication);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_UPDATE_FAILED);
//        }
//
//        return activityApplicationDTO;
//    }
}
