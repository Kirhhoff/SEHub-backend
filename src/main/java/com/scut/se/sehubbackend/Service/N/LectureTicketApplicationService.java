package com.scut.se.sehubbackend.Service.N;

import com.scut.se.sehubbackend.Domain.activityN.EtiquetteApplication;
import com.scut.se.sehubbackend.Domain.activityN.LectureTicketApplication;
import com.scut.se.sehubbackend.Repository.activityN.LectureTicketApplicationRepository;
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
