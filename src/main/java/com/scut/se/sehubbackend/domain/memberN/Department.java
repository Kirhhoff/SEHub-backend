package com.scut.se.sehubbackend.domain.memberN;

import com.scut.se.sehubbackend.enumeration.DepartmentNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

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

    @Id@GeneratedValue
    Long id;

    @NaturalId
    @Enumerated(value = EnumType.STRING)
    DepartmentNameEnum departmentName;//部门名称

    @OneToMany(mappedBy = "department")
    List<Member> memberList;//部门成员列表

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition = "text")
    String departmentDescription;//部门描述
}
