package com.scut.se.sehubbackend.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 海报申请表
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosterApplication {

    @Id@GeneratedValue
    Long id;

    @Embedded
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    CheckInfo checkInfo;////审核、发起者相关信息

    @Temporal(TemporalType.TIMESTAMP)
    Date deadline;//海报制作截止时间

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    String propagandaTextRequirement;//宣传文字要求

    String posterSize;//海报大小

    @OneToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    ActivityApplication activityThisBelongsTo;//该申请表所属于的活动（也可为空，说明这张申请表是独立的）
}
