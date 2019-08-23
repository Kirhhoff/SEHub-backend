package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.LectureTicketApplicationDTO;
import com.scut.se.sehubbackend.service.LectureTicketApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LectureTicketApplicationController {

    @Autowired
    LectureTicketApplicationService lectureTicketApplicationService;

    @PostMapping("/application/lecture-ticket")
    public void apply(@Valid LectureTicketApplicationDTO lectureTicketApplicationDTO) {
        lectureTicketApplicationService.create(lectureTicketApplicationDTO);
    }
}
