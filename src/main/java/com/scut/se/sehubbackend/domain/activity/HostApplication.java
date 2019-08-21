package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import lombok.*;

import javax.persistence.*;

/**
 * 主持人申请表
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostApplication implements Application {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    CheckInfo checkInfo;////审核、发起者相关信息

    Integer numOfHost;//要申请的主持人数量

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String descOfJob;//主持人工作描述

    @ToString.Exclude
    @OneToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY
    )
    ActivityApplication activityThisBelongsTo;//该申请表所属于的活动（也可为空，说明这张申请表是独立的）
}
