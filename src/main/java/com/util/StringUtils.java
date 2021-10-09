package com.util;

public class StringUtils {
    public static boolean isNotEmpty(String value) {
        return value != null && !"".equals(value.trim());
    }

    public static boolean isEmpty(String value) {
        return !isNotEmpty(value);
    }
}
