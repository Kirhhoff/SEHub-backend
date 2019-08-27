package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.HostApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.HostApplicationService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HostApplicationController {

    private final HostApplicationService hostApplicationService;
    private final DTOUtil dtoUtil;

    public HostApplicationController(HostApplicationService hostApplicationService, DTOUtil dtoUtil) {
        this.hostApplicationService = hostApplicationService;
        this.dtoUtil = dtoUtil;
    }

    @RequestMapping(value = "/application/host/{id}",method = RequestMethod.GET)
    public HostApplicationDTO getById(@PathVariable("id")Long id) throws InvalidIdException {
        return dtoUtil.toDTO(hostApplicationService.findById(id));
    }
    @PostMapping("/application/host")
    public void apply(@Valid@RequestBody HostApplicationDTO hostApplicationDTO) {
        hostApplicationService.create(hostApplicationDTO);
    }
}
