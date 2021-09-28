package com.scally.bottomlesshotbars.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUtils {

    public static final Pattern HOTBAR_NAME_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    public static boolean isValid(String name) {
        final Matcher matcher = HOTBAR_NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

}
