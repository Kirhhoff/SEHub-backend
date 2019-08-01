package com.scut.se.sehubbackend.Domain.memberNEW;

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
public class Authority {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_fk")
    private Member member;
}
