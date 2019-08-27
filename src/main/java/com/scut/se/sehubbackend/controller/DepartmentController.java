package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public DepartmentDTO get() {
        return departmentService.getCurrentDepartment();
    }

}
