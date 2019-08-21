package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EtiquetteApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    Integer numOfEtiquette;//要申请的礼仪人数

    Date rehearsalTime;//排练时间

    String rehearsalSite;//排练地点

    String descOfJob;//排练工作描述

    Long relatedActivity;//相关活动的id
}
