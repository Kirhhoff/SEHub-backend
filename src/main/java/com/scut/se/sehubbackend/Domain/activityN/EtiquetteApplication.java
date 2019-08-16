package com.scut.se.sehubbackend.Domain.activityN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 礼仪申请表
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetteApplication {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    Integer numOfEtiquette;//要申请的礼仪人数

    @Temporal(TemporalType.TIMESTAMP)
    Date rehearsalTime;//排练时间

    String rehearsalSite;//排练地点

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String descOfJob;//排练工作描述

    @OneToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    ActivityApplication activityThisBelongsTo;//该申请表所属于的活动（也可为空，说明这张申请表是独立的）
}
