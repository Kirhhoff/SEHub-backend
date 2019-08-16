package com.scut.se.sehubbackend.domain.member;

import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 团委学生会成员任职记录
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberServingRecord {

    @Id@GeneratedValue
    Long id;

    @ManyToOne
    Member owner;//记录所属成员

    Integer year;//任职年份

    @ManyToOne
    Department department;//当时所在部门

    @Enumerated(value = EnumType.STRING)
    PositionEnum position;//当时所任职位
}
