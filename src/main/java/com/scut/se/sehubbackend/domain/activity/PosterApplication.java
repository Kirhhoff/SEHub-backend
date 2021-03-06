package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 海报申请表
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosterApplication implements Application {

    @Id@GeneratedValue
    Long id;

    // 新增@
    @Embedded @NotNull
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded
    CheckInfo checkInfo;////审核、发起者相关信息

    @Temporal(TemporalType.TIMESTAMP) @NotNull
    Date deadline;//海报制作截止时间

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    @NotNull
    String propagandaTextRequirement;//宣传文字要求

    @NotNull
    String posterSize;//海报大小

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
