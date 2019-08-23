package com.scut.se.sehubbackend.domain.member;

import com.scut.se.sehubbackend.enumeration.PositionEnum;
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

    @OneToMany(
            mappedBy = "authorityOwner"
            ,cascade = CascadeType.ALL
    )
    List<Authority> authorityList;//该成员具备的所有权限

    public void addAuthority(Authority authority){
        for (Authority _authority: authorityList)
            if (_authority.getAuthorityName().equals(authority.getAuthorityName()))
                return;
        authority.setAuthorityOwner(this);
        authorityList.add(authority);
    }

    public void removeAuthority(Authority authority){
        int size=authorityList.size();
        for(int index=0;index<size;index++){
            Authority _authority=authorityList.get(index);
            if (_authority.authorityName.equals(authority.authorityName)){
                _authority.setAuthorityOwner(null);
                authorityList.remove(_authority);
            }
        }
    }
}
