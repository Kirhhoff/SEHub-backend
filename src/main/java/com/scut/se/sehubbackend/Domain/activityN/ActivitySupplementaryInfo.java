package com.scut.se.sehubbackend.Domain.activityNEW;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySupplementaryInfo {

    @Id
    @GeneratedValue
    private String id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String background;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String objective;

    private String organizer;

    private String hostUnit;

    private Integer expectedNumOfParticipants;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String note;

}
