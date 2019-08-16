package com.scut.se.sehubbackend.domain;

import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class NoticeDateComparator implements Comparator<Notice> {
    @Override
    public int compare(Notice o1, Notice o2) {
        return o1.getInitiateTime().compareTo(o2.getInitiateTime());
    }
}
