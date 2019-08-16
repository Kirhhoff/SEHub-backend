package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.activity.LectureTicketApplication;
import com.scut.se.sehubbackend.dao.activity.LectureTicketApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureTicketApplicationService {

    @Autowired
    private LectureTicketApplicationRepository lectureTicketApplicationRepository;

    public List<LectureTicketApplication> findAll() {
        return lectureTicketApplicationRepository.findAll();
    }

    public LectureTicketApplication findById(Long id) {
        return lectureTicketApplicationRepository.findById(id).orElse(null);
    }
}
