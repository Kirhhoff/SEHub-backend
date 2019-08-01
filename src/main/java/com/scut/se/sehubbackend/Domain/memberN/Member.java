package com.scut.se.sehubbackend.Domain.memberN;

import com.scut.se.sehubbackend.Domain.activityN.ActivityApplication;
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
    private Long studentNumber;

    private String name;

    private Integer position = PositionEnum.MINISTER.getCode();

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "department_name")
    private Department department;

    @OneToMany(mappedBy = "member")
    private List<Authority> authorityList;

    @OneToMany(mappedBy = "member")
    private List<MemberServedRecord> servedRecords;

    @OneToMany(mappedBy="initializer")
    List<ActivityApplication> activityApplicationList;

    @OneToMany(mappedBy="lastModifier")
    List<ActivityApplication> activityModificationList;

}
