package com.scut.se.sehubbackend.domain.member;

import com.scut.se.sehubbackend.enumeration.PositionEnum;
import lombok.*;

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

    @Column(name = "`position`")
    @Enumerated(value = EnumType.STRING)
    PositionEnum position;//职位

    String phoneNumber;//手机号

    String email;//邮箱

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "department_name")
    Department department;//所属部门

    @OneToMany(
            mappedBy = "authorityOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
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
