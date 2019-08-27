package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.EtiquetteApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.EtiquetteApplicationService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EtiquetteApplicationController {

    private final EtiquetteApplicationService etiquetteApplicationService;
    private final DTOUtil dtoUtil;

    public EtiquetteApplicationController(EtiquetteApplicationService etiquetteApplicationService, DTOUtil dtoUtil) {
        this.etiquetteApplicationService = etiquetteApplicationService;
        this.dtoUtil = dtoUtil;
    }

    @RequestMapping(value = "/application/etiquette/{id}",method = RequestMethod.GET)
    public EtiquetteApplicationDTO getById(@PathVariable("id")Long id) throws InvalidIdException {
        return dtoUtil.toDTO(etiquetteApplicationService.findById(id));
    }

    @PostMapping("/application/etiquette")
    public void apply(@Valid@RequestBody EtiquetteApplicationDTO etiquetteApplicationDTO) {
        etiquetteApplicationService.create(etiquetteApplicationDTO);
    }


}
