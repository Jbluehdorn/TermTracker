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
}
