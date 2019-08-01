package com.scut.se.sehubbackend.utils;

import java.util.Random;

public class KeyUtil {

    public static synchronized Long genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + number;
    }
}
