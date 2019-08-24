package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import com.scut.se.sehubbackend.enumeration.TicketType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LectureTicketApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    Integer numOfTicket;//讲座票数量

    Double ticketScore;//讲座票分值

    TicketType ticketType;//讲座票类型

    Long relatedActivity;//相关活动的id
}
