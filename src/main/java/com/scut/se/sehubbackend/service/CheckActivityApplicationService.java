package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.enumeration.ResultEnum;
import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.exception.ActivityApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckActivityApplicationService {
//
//    @Autowired
//    ActivityApplicationService activityApplicationService;
//
//    public ActivityApplicationDTO pass(Long id) {
//        ActivityApplicationDTO activityApplicationDTO = activityApplicationService.findById(id);
//
//        if (activityApplicationDTO == null) {
//            log.error("【审核通过】查找活动申请失败, activityId={}", id);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_NOT_EXIST);
//        }
//
//        return activityApplicationService.pass(activityApplicationDTO);
//    }
//
//    public ActivityApplicationDTO pass(Long id, String checkFeedback) {
//        ActivityApplicationDTO activityApplicationDTO = activityApplicationService.findById(id);
//        activityApplicationDTO.setCheckFeedback(checkFeedback);
//
//        if (activityApplicationDTO == null) {
//            log.error("【审核通过】查找活动申请失败, activityId={}", id);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_NOT_EXIST);
//        }
//
//        return activityApplicationService.nopass(activityApplicationDTO);
//    }
}
