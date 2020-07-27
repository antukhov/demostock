package com.stock.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessing {

    public final static String COMMAND_REGEX = "^(add|cancel) [A-Z]{3,} (B|S) [0-9]* [1-9]\\d*(\\.\\d\\d)?$";

    public static boolean validateInput(String input) {
        Pattern pattern = Pattern.compile(COMMAND_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input.trim());
        return matcher.matches();
    }

    public static List<String> getListFromString(String input) {
        return new LinkedList<>(Arrays.asList(input.split(" ")));
    }

}
