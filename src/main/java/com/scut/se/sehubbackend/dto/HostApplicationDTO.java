package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityMainInfo;
import com.scut.se.sehubbackend.domain.activity.HostApplication;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HostApplicationDTO {

    public static final String TYPE= HostApplication.class.getTypeName();

    Long id;

    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    Integer numOfHost;//要申请的主持人数量

    String descOfJob;//主持人工作描述

    String type;//申请表类别（礼仪、主持人..）

    Boolean hasRelatedActivityApplication;//是否有相关的活动申请表
}
