package com.booxj.tools.core.data;

import java.time.format.DateTimeFormatter;

public enum DateFormatter {
    YYYYMMDD(DateTimeFormatter.ofPattern("yyyyMMdd")),
    YYYY_MM_DD(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    HH_MM_SS(DateTimeFormatter.ofPattern("HH:mm:ss")),
    YYYY_MM_DD_HH_MM_SS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    YYYY_MM_DD_HH_MM_SS_SSS(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
    CHINESE_YYYY_MM_DD(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),
    CHINESE_YYYY_MM_DD_HH_MM_SS(DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒"));

    private DateTimeFormatter formatter;

    DateFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

}
