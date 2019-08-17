package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ActivityApplicationDTO {

    Long id;

    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    ActivitySupplementaryInfo activitySupplementaryInfo;//活动申请表的补充信息，包括{背景，受众，主办方}等等

    @Singular
    List<Object> subApplications;//相关的子申请表（礼仪、主持人..）

    CheckStatusEnum checkStatus;//申请表的审核状态

    String checkFeedback;//审核后的反馈

    Date submissionDate;//活动的提交日期(自动生成)

    Date checkDate;//审核日期

    Member initializer;//申请表发起人

    Member lastModifier;//申请表的最后修改人
}
