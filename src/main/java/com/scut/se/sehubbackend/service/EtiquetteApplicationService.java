package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.activity.EtiquetteApplication;
import com.scut.se.sehubbackend.dao.activity.EtiquetteApplicationRepository;
import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetteApplicationService {

    @Autowired
    private EtiquetteApplicationRepository etiquetteApplicationRepository;

    public List<EtiquetteApplication> findAll() {
        return etiquetteApplicationRepository.findAll();
    }

    public EtiquetteApplication findById(Long id) {
        return etiquetteApplicationRepository.findById(id).orElse(null);
    }

    public void create(EtiquetteApplicationDTO etiquetteApplicationDTO){
        create(etiquetteApplicationDTO,null);
    }

    public void create(EtiquetteApplicationDTO etiquetteApplicationDTO,
                       ActivityApplication activityApplication){

    }
}
