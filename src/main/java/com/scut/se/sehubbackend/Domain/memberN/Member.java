package com.scut.se.sehubbackend.Domain.memberNEW;

import com.scut.se.sehubbackend.Domain.activityNEW.ActivityApplication;
import com.scut.se.sehubbackend.Enumeration.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String studentNumber;

    private String name;

    private Integer position = PositionEnum.MINISTER.getCode();

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "department_fk")
    private Department department;

    @OneToMany(mappedBy = "member")
    private List<Authority> authorityList;

    @OneToMany(mappedBy = "member")
    private List<MemberServedRecords> servedRecords;

    @OneToMany(mappedBy="initializer")
    List<ActivityApplication> activityApplicationList;

    @OneToMany(mappedBy="lastModifier")
    List<ActivityApplication> activityModificationList;

}
