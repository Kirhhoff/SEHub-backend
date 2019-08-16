package com.scut.se.sehubbackend.domain.activityN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 讲座篇申请表
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureTicketApplication {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    Integer numOfTicket;//讲座票数量

    @OneToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    ActivityApplication activityThisBelongsTo;//该申请表所属于的活动（也可为空，说明这张申请表是独立的）
}
