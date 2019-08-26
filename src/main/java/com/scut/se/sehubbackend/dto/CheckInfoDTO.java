package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfoDTO {
    CheckStatusEnum checkStatus;//申请表的审核状态

    String checkFeedback;//审核后的反馈

    Date submissionDate;//申请表的提交日期(自动生成)

    Date checkDate;//审核日期

    MemberDTO initializer;//申请表发起人

    MemberDTO lastModifier;//申请表的最后修改人
}
