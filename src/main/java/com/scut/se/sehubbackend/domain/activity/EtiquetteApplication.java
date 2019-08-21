package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 礼仪申请表
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetteApplication implements Application {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    @NotNull
    CheckInfo checkInfo;////审核、发起者相关信息

    @NotNull
    Integer numOfEtiquette;//要申请的礼仪人数

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    Date rehearsalTime;//排练时间

    @NotNull
    String rehearsalSite;//排练地点

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    @NotNull
    String descOfJob;//排练工作描述

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
