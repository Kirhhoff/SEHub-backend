package com.scut.se.sehubbackend.Domain.activityN;

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
    @Column(name="activity_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="activity_id", referencedColumnName="activity_id")
    private ActivityMainInfo activityMainInfo;

    private Integer numOfTicket;

}
