package com.scut.se.sehubbackend.Domain.memberN;

import com.scut.se.sehubbackend.Enumeration.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 团委学生会成员
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    Long studentNumber;//学号

    String password;//密码

    String name;//成员姓名

    @Enumerated(value = EnumType.STRING)
    PositionEnum position;//职位

    String phoneNumber;//手机号

    @ManyToOne
    @JoinColumn(name = "department_name")
    Department department;//所属部门

    @OneToMany(mappedBy = "authorityOwner")
    List<Authority> authorityList;//该成员具备的所有权限
}
