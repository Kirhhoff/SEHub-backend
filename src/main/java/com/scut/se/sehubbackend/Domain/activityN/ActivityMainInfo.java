package com.scut.se.sehubbackend.Domain.activityN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMainInfo {

    @Id
    private Long activityId;

    private String name;

    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
    private Date endTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String description;
}
