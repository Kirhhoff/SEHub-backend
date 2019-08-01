package com.scut.se.sehubbackend.Domain.memberN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberServedRecords {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name="student_number")
    private Member member;

    private Integer year;

    private String departmentName;

    private Integer position;

}
