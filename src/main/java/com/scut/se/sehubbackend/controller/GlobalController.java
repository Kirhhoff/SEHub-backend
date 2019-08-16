package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {

    @Autowired
    AuthorizeService authorizeService;

}
