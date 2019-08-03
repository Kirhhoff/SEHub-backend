package com.scut.se.sehubbackend.component;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("/empty")
    public void empty(){ }

}
