package com.scut.se.sehubbackend.domain.member;

import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 部门
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Enumerated(value = EnumType.STRING)
    DepartmentNameEnum departmentName;//部门名称

    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    List<Member> memberList;//部门成员列表

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition = "text")
    String departmentDescription;//部门描述

    public void addMember(Member member){
        if (!memberList.contains(member)){
            memberList.add(member);
            member.setDepartment(this);
        }
    }

    public void removeMember(Member member){
        if (memberList.contains(member)){
            member.setDepartment(null);
            memberList.remove(member);
        }
    }
}
