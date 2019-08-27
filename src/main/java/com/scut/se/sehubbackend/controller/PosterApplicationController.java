package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.PosterApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.PosterApplicationService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PosterApplicationController {

    private final PosterApplicationService posterApplicationService;
    private final DTOUtil dtoUtil;

    public PosterApplicationController(PosterApplicationService posterApplicationService, DTOUtil dtoUtil) {
        this.posterApplicationService = posterApplicationService;
        this.dtoUtil = dtoUtil;
    }

    @RequestMapping(value = "/application/poster/{id}",method = RequestMethod.GET)
    public PosterApplicationDTO getById(@PathVariable("id")Long id) throws InvalidIdException {
        return dtoUtil.toDTO(posterApplicationService.findById(id));
    }
    @PostMapping("/application/poster")
    public void apply(@Valid@RequestBody PosterApplicationDTO posterApplicationDTO) {
        posterApplicationService.create(posterApplicationDTO);
    }
}
