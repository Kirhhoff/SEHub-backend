package com.scut.se.sehubbackend.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "student_number")
    Member authorityOwner;//权限所属的成员

    String authorityName;//权限名称
}
