package com.scut.se.sehubbackend.Domain.activityNEW;

import com.scut.se.sehubbackend.Domain.memberNEW.Member;
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
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name="activityMainInfo_fk")
    private ActivityMainInfo activityMainInfo;

    @OneToOne
    @JoinColumn(name="activitySupplementaryInfo_fk")
    private ActivitySupplementaryInfo activitySupplementaryInfo;

    @OneToOne
    @JoinColumn(name="etiquetteApplication_fk")
    private EtiquetteApplication etiquetteApplication;

    @OneToOne
    @JoinColumn(name="hostApplication_fk")
    private HostApplication hostApplication;

    @OneToOne
    @JoinColumn(name="lectureTicketApplication_fk")
    private LectureTicketApplication lectureTicketApplication;

    @OneToOne
    @JoinColumn(name="posterApplication_fk")
    private PosterApplication posterApplication;

    private Integer checkStatus = CheckStatusEnum.WAIT.getCode();

    @Temporal(TemporalType.DATE)
    private Date submissionDate;

    @Temporal(TemporalType.DATE)
    private Date checkDate;

    @ManyToOne
    @JoinColumn(name = "initializer_fk")
    private Member initializer;

    @ManyToOne
    @JoinColumn(name = "lastModifier_fk")
    private Member lastModifier;

}
