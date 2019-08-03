package com.scut.se.sehubbackend.Service.N;

import com.scut.se.sehubbackend.Domain.activityN.EtiquetteApplication;
import com.scut.se.sehubbackend.Repository.activityN.EtiquetteApplicationRepository;
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
}