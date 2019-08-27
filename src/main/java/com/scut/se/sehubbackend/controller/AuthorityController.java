package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.AuthorityService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("/authority/add/{id}")
    public void add(@PathVariable Long id, @RequestParam("authority") AuthorityEnum authority) throws InvalidIdException {
        authorityService.grant(id, authority);
    }

    @PostMapping("/authority/remove/{id}")
    public void remove(@PathVariable Long id, @RequestParam("authority") AuthorityEnum authority) throws InvalidIdException {
        authorityService.remove(id, authority);
    }
}
