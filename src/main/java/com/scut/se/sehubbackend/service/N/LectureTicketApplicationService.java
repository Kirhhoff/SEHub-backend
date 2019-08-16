package com.scut.se.sehubbackend.service.N;

import com.scut.se.sehubbackend.domain.activityN.LectureTicketApplication;
import com.scut.se.sehubbackend.dao.activityN.LectureTicketApplicationRepository;
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
