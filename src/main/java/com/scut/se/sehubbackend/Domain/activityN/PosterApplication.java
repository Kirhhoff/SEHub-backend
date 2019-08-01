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
public class PosterApplication {

    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name="activityMainInfo_fk")
    private ActivityMainInfo activityMainInfo;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String propagandaTextRequirement;

    private Integer posterSize;

}
