package com.scut.se.sehubbackend.Domain.memberN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_number")
    private Member member;
}
