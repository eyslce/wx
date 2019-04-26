package com.eyslce.wx.commons.util;

import java.util.Random;
import java.util.UUID;

public class StringUtils {

    private static final String STRING_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    public static String token() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String wxAesKey() {
        return randomStr(STRING_POOL, 43);
    }

    public static String randomStr(String pool, int length) {
        StringBuilder sb = new StringBuilder();
        int bound = pool.length() - 1;
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            sb.append(pool.charAt(random.nextInt(bound)));
        }
        return sb.toString();
    }
}
