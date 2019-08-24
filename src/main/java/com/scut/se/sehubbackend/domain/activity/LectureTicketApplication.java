package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import com.scut.se.sehubbackend.enumeration.TicketType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    Integer numOfTicket;//讲座票数量

    @NotNull
    TicketType ticketType;//讲座票类型

    @NotNull
    Double ticketScore;//讲座票分值

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
