package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import lombok.*;

import javax.persistence.*;

/**
 * 讲座篇申请表
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureTicketApplication implements Application {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    CheckInfo checkInfo;////审核、发起者相关信息

    Integer numOfTicket;//讲座票数量

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
