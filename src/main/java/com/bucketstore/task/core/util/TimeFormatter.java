package com.bucketstore.task.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(FORMATTER);
    }

}
