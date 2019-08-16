package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.activity.HostApplication;
import com.scut.se.sehubbackend.dao.activity.HostApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostApplicationService {

    @Autowired
    private HostApplicationRepository hostApplicationRepository;

    public List<HostApplication> findAll() {
        return hostApplicationRepository.findAll();
    }

    public HostApplication findById(Long id) {
        return hostApplicationRepository.findById(id).orElse(null);
    }
}
