package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.dto.DepartmentDTO;
import com.scut.se.sehubbackend.service.DepartmentService;
import com.scut.se.sehubbackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/department")
    public DepartmentDTO get() {
        return departmentService.getCurrentDepartment();
    }

}
