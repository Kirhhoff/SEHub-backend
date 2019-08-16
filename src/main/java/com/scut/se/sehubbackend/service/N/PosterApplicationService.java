package com.scut.se.sehubbackend.service.N;

import com.scut.se.sehubbackend.domain.activityN.PosterApplication;
import com.scut.se.sehubbackend.dao.activityN.PosterApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterApplicationService {

    @Autowired
    private PosterApplicationRepository posterApplicationRepository;

    public List<PosterApplication> findAll() {
        return posterApplicationRepository.findAll();
    }

    public PosterApplication findById(Long id) {
        return posterApplicationRepository.findById(id).orElse(null);
    }
}
