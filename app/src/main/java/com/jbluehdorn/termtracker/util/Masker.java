package com.jbluehdorn.termtracker.util;

import android.util.Log;

public class Masker {
    public static String maskDate(String input) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0, len = input.length(); i < len; i++) {
            if(i > 7) {
                break;
            }

            if(i == 2 || i == 4) {
                sb.append('/');
            }

            sb.append(input.charAt(i));
        }

        return sb.toString();
    }

    public static String maskPhone(String input) {
        StringBuilder sb = new StringBuilder();
        String slice = input.length() > 10 ? input.substring(0, 10) : input;

        for(int i = slice.length() - 1, counter = 0; i >= 0; i--, counter++) {
            sb.append(slice.charAt(i));

            if(counter == 3 && i != 0) {
                sb.append('-');
            }

            if(counter == 6 && i != 0) {
                sb.append(')');
            }

            if(counter == 9) {
                sb.append('(');
            }
        }

        return sb.reverse().toString();
    }
}
