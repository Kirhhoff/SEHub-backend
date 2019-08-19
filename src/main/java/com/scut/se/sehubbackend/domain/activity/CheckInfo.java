package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 审核、发起者等等表的通有信息
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInfo {

    @Enumerated(value = EnumType.STRING)
    CheckStatusEnum checkStatus;//申请表的审核状态

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String checkFeedback;//审核后的反馈

    @Temporal(TemporalType.DATE)
    Date submissionDate;//申请表的提交日期

    @Temporal(TemporalType.DATE)
    Date checkDate;//审核日期

    @ManyToOne
    Member initializer;//申请表发起人

    @ManyToOne
    Member lastModifier;//申请表的最后修改人
}
