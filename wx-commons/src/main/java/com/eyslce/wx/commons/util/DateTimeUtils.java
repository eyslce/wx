package com.eyslce.wx.commons.util;

import java.util.Date;

public class DateTimeUtils {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static Date formatSecondUnitToDate(long time) {
        return new Date(time * 1000);
    }
}
