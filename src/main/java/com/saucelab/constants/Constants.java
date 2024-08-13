package com.saucelab.constants;

public class Constants {
    public static String URL;
    public static String STANDARD_USER;
    public static String LOCKED_OUT_USER;
    public static String PROBLEM_USER;
    public static String PERFORMANCE_GLITCH_USER;
    public static String ERROR_USER;
    public static String VISUAL_USER;
    public static String PASSWORD;

    public static void setURL(String URL) {
        Constants.URL = URL;
    }

    public static void setStandardUser(String standardUser) {
        STANDARD_USER = standardUser;
    }

    public static void setLockedOutUser(String lockedOutUser) {
        LOCKED_OUT_USER = lockedOutUser;
    }

    public static void setProblemUser(String problemUser) {
        PROBLEM_USER = problemUser;
    }

    public static void setPerformanceGlitchUser(String performanceGlitchUser) {
        PERFORMANCE_GLITCH_USER = performanceGlitchUser;
    }

    public static void setErrorUser(String errorUser) {
        ERROR_USER = errorUser;
    }

    public static void setVisualUser(String visualUser) {
        VISUAL_USER = visualUser;
    }

    public static void setPassword(String PASSWORD) {
        Constants.PASSWORD = PASSWORD;
    }
}
