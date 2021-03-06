package com.scut.se.sehubbackend.domain.activity;

import com.scut.se.sehubbackend.domain.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 活动申请表
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityApplication implements Application {

    @Id@GeneratedValue
    Long id;

    // 这里原本没有@NotNull
    @Embedded @NotNull
    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @Embedded @NotNull
    ActivitySupplementaryInfo activitySupplementaryInfo;//活动申请表的补充信息，包括{背景，受众，主办方}等等

    @Embedded
    CheckInfo checkInfo;//审核、发起者相关信息

    @OneToOne(
            mappedBy = "activityThisBelongsTo",
            cascade = CascadeType.ALL
    )
    EtiquetteApplication etiquetteApplication;//相关的礼仪申请（可为空）

    @OneToOne(
            mappedBy = "activityThisBelongsTo",
            cascade = CascadeType.ALL
    )
    HostApplication hostApplication;//相关的主持人申请（可为空）

    @OneToOne(
            mappedBy = "activityThisBelongsTo",
            cascade = CascadeType.ALL
    )
    LectureTicketApplication lectureTicketApplication;//相关的讲座票申请（可为空）

    @OneToOne(
            mappedBy = "activityThisBelongsTo",
            cascade = CascadeType.ALL
    )
    PosterApplication posterApplication;//相关的海报申请（可为空）

    public void setEtiquetteApplication(EtiquetteApplication etiquetteApplication){
        assert(etiquetteApplication!=null);
        this.etiquetteApplication=etiquetteApplication;
        etiquetteApplication.setActivityThisBelongsTo(this);
    }

    public void setHostApplication(HostApplication hostApplication){
        assert(hostApplication!=null);
        this.hostApplication=hostApplication;
        hostApplication.setActivityThisBelongsTo(this);
    }

    public void setLectureTicketApplication(LectureTicketApplication lectureTicketApplication){
        assert(lectureTicketApplication!=null);
        this.lectureTicketApplication=lectureTicketApplication;
        lectureTicketApplication.setActivityThisBelongsTo(this);
    }

    public void setPosterApplication(PosterApplication posterApplication){
        assert(posterApplication!=null);
        this.posterApplication=posterApplication;
        posterApplication.setActivityThisBelongsTo(this);
    }
}
