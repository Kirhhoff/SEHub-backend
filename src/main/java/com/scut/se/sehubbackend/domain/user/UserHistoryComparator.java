package com.scut.se.sehubbackend.domain.user;

import java.util.Comparator;

public class UserHistoryComparator implements Comparator<UserHistory> {
    @Override
    public int compare(UserHistory o1, UserHistory o2) {
        return o1.getYear().compareTo(o2.getYear());
    }
}
