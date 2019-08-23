package com.scut.se.sehubbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.domain.activity.ActivitySupplementaryInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ActivityApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    ActivitySupplementaryInfo activitySupplementaryInfo;//活动申请表的补充信息，包括{背景，受众，主办方}等等

    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    EtiquetteApplicationDTO etiquetteApplication;//DTO形式的礼仪申请

    HostApplicationDTO hostApplication;//DTO形式的主持人申请

    LectureTicketApplicationDTO lectureTicketApplication;//DTO形式的讲座票申请

    @JsonProperty("poster")
    PosterApplicationDTO posterApplication;//DTO形式的海报申请
}
