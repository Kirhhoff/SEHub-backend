package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.ApplicationNotice;
import com.scut.se.sehubbackend.service.NoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice/application")
    public List<ApplicationNotice> list() {
        return noticeService.getApplicationNotices();
    }
}
