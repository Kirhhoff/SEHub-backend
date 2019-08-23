package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class HostApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    Integer numOfHost;//要申请的主持人数量

    String descOfJob;//主持人工作描述

    Long relatedActivity;//相关活动的id
}
