package com.ederfmatos.burguerbot.utils;

import java.time.LocalTime;

public final class BurguerBotUtils {

    private BurguerBotUtils() {
    }

    public static String getSalutation() {
        int hour = LocalTime.now().getHour();

        if (hour >= 5 && hour < 12) {
            return "Bom dia";
        }

        if (hour < 18) {
            return "Boa tarde";
        }

        return "Boa noite";
    }

    public static boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
