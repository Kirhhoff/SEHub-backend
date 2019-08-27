package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.LectureTicketApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.LectureTicketApplicationService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LectureTicketApplicationController {

    private final LectureTicketApplicationService lectureTicketApplicationService;
    private final DTOUtil dtoUtil;

    public LectureTicketApplicationController(LectureTicketApplicationService lectureTicketApplicationService, DTOUtil dtoUtil) {
        this.lectureTicketApplicationService = lectureTicketApplicationService;
        this.dtoUtil = dtoUtil;
    }

    @RequestMapping(value = "/application/lecture-ticket/{id}",method = RequestMethod.GET)
    public LectureTicketApplicationDTO getById(@PathVariable("id")Long id) throws InvalidIdException {
        return dtoUtil.toDTO(lectureTicketApplicationService.findById(id));
    }
    @PostMapping("/application/lecture-ticket")
    public void apply(@Valid @RequestBody LectureTicketApplicationDTO lectureTicketApplicationDTO) {
        lectureTicketApplicationService.create(lectureTicketApplicationDTO);
    }
}
