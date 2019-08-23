package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.service.EtiquetteApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EtiquetteApplicationController {

    @Autowired
    EtiquetteApplicationService etiquetteApplicationService;

    @PostMapping("/application/etiquette")
    public void apply(@Valid EtiquetteApplicationDTO etiquetteApplicationDTO) {
        etiquetteApplicationService.create(etiquetteApplicationDTO);
    }
}
