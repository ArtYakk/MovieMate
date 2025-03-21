package com.artemyakkonen.spring.boot.moviemate.util;

import jakarta.persistence.Column;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.ansi.AnsiColor;

public class AnsiColors {
    public static final String RESET = "\u001B[0m";
    public static final String ES = "\u001B[";
    public static final String CAPE = "m";

    public static String colorize(Object text, AnsiColor color) {
        return escape(color) + text.toString() + RESET;
    }

    public static String background(Object text, AnsiBackground background) {
        return escape(background) + text.toString() + RESET;
    }

    public static String blackOnBlue(Object text) {
        return escape(AnsiColor.BLACK) + escape(AnsiBackground.BRIGHT_BLUE) + text.toString() + RESET;
    }

    //-------------------------------------------------------------------------//

    public static String escape(AnsiColor color) {
        return ES + color + CAPE;
    }

    public static String escape(AnsiBackground background) {
        return ES + background + CAPE;
    }
}
