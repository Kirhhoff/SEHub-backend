package com.scut.se.sehubbackend.domain.activityN;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * <p>各申请表都必须有的信息</p>
 * <p>包括活动名称、地点、开始时间等等</p>
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMainInfo {

    String name;//活动名称

    String location;//活动地点

    @Temporal(TemporalType.TIMESTAMP)
    Date startTime;//活动开始时间

    @Temporal(TemporalType.TIMESTAMP)
    Date endTime;//活动结束时间

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String description;//活动说明
}
