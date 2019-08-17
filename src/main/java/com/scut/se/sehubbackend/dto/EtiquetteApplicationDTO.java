package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityMainInfo;
import com.scut.se.sehubbackend.domain.activity.EtiquetteApplication;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EtiquetteApplicationDTO {

    public static final String TYPE= EtiquetteApplication.class.getTypeName();

    Long id;

    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    Integer numOfEtiquette;//要申请的礼仪人数

    Date rehearsalTime;//排练时间

    String rehearsalSite;//排练地点

    String descOfJob;//排练工作描述

    String type;//申请类型

    Boolean hasRelatedActivityApplication;//是否有相关的活动申请表
}
