package com.scut.se.sehubbackend.Domain.activityNEW;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureTicketApplication {

    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name="activityMainInfo_fk")
    private ActivityMainInfo activityMainInfo;

    private Integer numOfTicket;

}
