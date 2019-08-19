package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.HostApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.HostApplication;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.dto.HostApplicationDTO;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostApplicationService {

    final HostApplicationRepository hostApplicationRepository;
    final CheckInfoUtil checkInfoUtil;

    public HostApplicationService(HostApplicationRepository hostApplicationRepository, CheckInfoUtil checkInfoUtil) {
        this.hostApplicationRepository = hostApplicationRepository;
        this.checkInfoUtil = checkInfoUtil;
    }

    public List<HostApplication> findAll() {
        return hostApplicationRepository.findAll();
    }

    public HostApplication findById(Long id) {
        return hostApplicationRepository.findById(id).orElse(null);
    }

    /**
     * 参考{@link EtiquetteApplicationService#create(EtiquetteApplicationDTO)}
     */
    public void create(HostApplicationDTO hostApplicationDTO){
        HostApplication hostApplication= toDO(hostApplicationDTO,null);
        hostApplicationRepository.saveAndFlush(hostApplication);
    }

    /**
     * 参考{@link EtiquetteApplicationService#toDO(EtiquetteApplicationDTO, ActivityApplication)}
     */
    public HostApplication toDO(HostApplicationDTO hostApplicationDTO,
                       ActivityApplication parentActivityApplication){
        return HostApplication.builder()
                .activityBasicInfo(hostApplicationDTO.getActivityBasicInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .numOfHost(hostApplicationDTO.getNumOfHost())
                .descOfJob(hostApplicationDTO.getDescOfJob())
                .activityThisBelongsTo(parentActivityApplication)
                .build();
    }
}
