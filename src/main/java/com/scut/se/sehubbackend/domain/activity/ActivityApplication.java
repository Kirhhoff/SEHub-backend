package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动申请表
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityApplication {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    ActivitySupplementaryInfo activitySupplementaryInfo;//活动申请表的补充信息，包括{背景，受众，主办方}等等

    @OneToOne(mappedBy = "activityThisBelongsTo")
    EtiquetteApplication etiquetteApplication;//相关的礼仪申请（可为空）

    @OneToOne(mappedBy = "activityThisBelongsTo")
    HostApplication hostApplication;//相关的主持人申请（可为空）

    @OneToOne(mappedBy = "activityThisBelongsTo")
    LectureTicketApplication lectureTicketApplication;//相关的讲座票申请（可为空）

    @OneToOne(mappedBy = "activityThisBelongsTo")
    PosterApplication posterApplication;//相关的海报申请（可为空）

    @Enumerated(value = EnumType.STRING)
    CheckStatusEnum checkStatus;//申请表的审核状态

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String checkFeedback;//审核后的反馈

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    Date submissionDate;//活动的提交日期(自动生成)

    @Temporal(TemporalType.DATE)
    Date checkDate;//审核日期

    @ManyToOne
    Member initializer;//申请表发起人

    @ManyToOne
    Member lastModifier;//申请表的最后修改人

}
