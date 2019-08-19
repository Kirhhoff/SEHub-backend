package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.PosterApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.PosterApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.PosterApplicationDTO;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterApplicationService {

    final PosterApplicationRepository posterApplicationRepository;
    final CheckInfoUtil checkInfoUtil;

    public PosterApplicationService(PosterApplicationRepository posterApplicationRepository, CheckInfoUtil checkInfoUtil) {
        this.posterApplicationRepository = posterApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }

    public List<PosterApplication> findAll() {
        return posterApplicationRepository.findAll();
    }

    public PosterApplication findById(Long id) {
        return posterApplicationRepository.findById(id).orElse(null);
    }

    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(PosterApplicationDTO posterApplicationDTO){
        PosterApplication posterApplication=toDO(posterApplicationDTO,null);
        posterApplicationRepository.saveAndFlush(posterApplication);
    }

    /**
     * 参考{@link EtiquetteApplicationService#toDO(EtiquetteApplicationDTO, ActivityApplication)}
     */
    public PosterApplication toDO(PosterApplicationDTO posterApplicationDTO,
                       ActivityApplication parentActivityApplication){
        return PosterApplication.builder()
                .activityBasicInfo(posterApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .deadline(posterApplicationDTO.getDeadline())
                .propagandaTextRequirement(posterApplicationDTO.getPropagandaTextRequirement())
                .posterSize(posterApplicationDTO.getPosterSize())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }
}
