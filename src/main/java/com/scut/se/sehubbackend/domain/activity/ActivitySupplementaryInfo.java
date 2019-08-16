package com.scut.se.sehubbackend.domain.activity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 对活动申请表的补充信息，包括活动背景、目标受众等等
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySupplementaryInfo {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String background;//活动背景

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String objective;//活动的目标受众

    String organizer;//活动隶属的组织

    String hostUnit;//活动主办方

    Integer expectedNumOfParticipants;//预计参与人数

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String note;//补充说明
}
