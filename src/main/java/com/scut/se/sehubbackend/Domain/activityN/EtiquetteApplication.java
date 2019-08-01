package com.scut.se.sehubbackend.Domain.activityN;

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
    @Column(name="activity_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private ActivityMainInfo activityMainInfo;

    private Integer numOfEtiquette;

    @Temporal(TemporalType.DATE)
    private Date rehearsalTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String descOfJob;
}
