package com.scut.se.sehubbackend.Domain;

import com.scut.se.sehubbackend.Enumeration.Department;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Blob;

@Entity
public class DepartmentInformation implements Serializable {

    private static final Long serialVersionUID=7L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @NaturalId
    Department department;

    @NotNull
    int memberAmount;

    @Lob
    String introduction;

    @Lob
    Blob avatar;
}