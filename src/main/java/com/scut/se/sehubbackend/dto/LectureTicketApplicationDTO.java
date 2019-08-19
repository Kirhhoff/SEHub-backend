package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LectureTicketApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    Integer numOfTicket;//讲座票数量

    Boolean hasRelatedActivityApplication;//是否有相关的活动申请表
}
