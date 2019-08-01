package com.scut.se.sehubbackend.Domain.activityNEW;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostApplication {

    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name="activityMainInfo_fk")
    private ActivityMainInfo activityMainInfo;

    private Integer numOfHost;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String descOfJob;

}
