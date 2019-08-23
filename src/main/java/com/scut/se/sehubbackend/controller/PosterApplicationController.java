package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.PosterApplicationDTO;
import com.scut.se.sehubbackend.service.PosterApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PosterApplicationController {

    @Autowired
    PosterApplicationService posterApplicationService;

    @PostMapping("/application/poster")
    public void apply(@Valid PosterApplicationDTO posterApplicationDTO) {
        posterApplicationService.create(posterApplicationDTO);
    }
}
