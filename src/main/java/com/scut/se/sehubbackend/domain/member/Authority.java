package com.scut.se.sehubbackend.domain.member;

import lombok.*;

import javax.persistence.*;

/**
 * 权限记录
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id@GeneratedValue
    Long id;

    @ManyToOne
    @ToString.Exclude
    Member authorityOwner;//权限所属的成员

    String authorityName;//权限名称
}
