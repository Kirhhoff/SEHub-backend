package com.scut.se.sehubbackend.Domain.activityNEW;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetteApplication {

    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name="activityMainInfo_fk")
    private ActivityMainInfo activityMainInfo;

    private Integer numOfEtiquette;

    @Temporal(TemporalType.DATE)
    private Date rehearsalTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String descOfJob;
}
