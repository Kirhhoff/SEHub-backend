package com.scut.se.sehubbackend.Domain.activityN;

import com.scut.se.sehubbackend.Domain.memberN.Member;
import com.scut.se.sehubbackend.Enumeration.CheckStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityApplication {

    @Id
    @Column(name="activity_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private ActivityMainInfo activityMainInfo;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private ActivitySupplementaryInfo activitySupplementaryInfo;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private EtiquetteApplication etiquetteApplication;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private HostApplication hostApplication;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private LectureTicketApplication lectureTicketApplication;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private PosterApplication posterApplication;

    private Integer checkStatus = CheckStatusEnum.WAIT.getCode();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String checkFeedback;

    @Temporal(TemporalType.DATE)
    @org.hibernate.annotations.CreationTimestamp
    private Date submissionDate;

    @Temporal(TemporalType.DATE)
    private Date checkDate;

    @ManyToOne
    @JoinColumn(name = "initializer_id")
    private Member initializer;

    @ManyToOne
    @JoinColumn(name = "lastModifier_id")
    private Member lastModifier;

}
