package com.scut.se.sehubbackend.controller;

import com.mysql.cj.protocol.x.Notice;
import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/notice/application")
    public List<ApplicationNotice> list() {
        return noticeService.getApplicationNotices();
    }
}
