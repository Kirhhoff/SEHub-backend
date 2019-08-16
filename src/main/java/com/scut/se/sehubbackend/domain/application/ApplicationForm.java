package com.scut.se.sehubbackend.domain.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ApplicationForm implements Serializable {

    private static final Long serialVersionUID=6L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    ApplicationInternalInformation internalInformation;

    @OneToOne
    ApplicationJoinInformation joinInformation;
}
