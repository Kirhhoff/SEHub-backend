package com.scut.se.sehubbackend.Domain.memberNEW;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    private String name;

    private Integer numOfMember;

    @OneToMany(mappedBy = "department")
    private List<Member> memberList;

}
