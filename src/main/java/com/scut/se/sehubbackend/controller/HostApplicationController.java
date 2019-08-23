package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.HostApplicationDTO;
import com.scut.se.sehubbackend.service.HostApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HostApplicationController {

    @Autowired
    HostApplicationService hostApplicationService;

    @PostMapping("/application/host")
    public void apply(@Valid HostApplicationDTO hostApplicationDTO) {
        hostApplicationService.create(hostApplicationDTO);
    }
}
