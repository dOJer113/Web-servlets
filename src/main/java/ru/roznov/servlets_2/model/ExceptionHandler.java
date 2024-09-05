package ru.roznov.servlets_2.model;

public class ExceptionHandler {
    public static void handleException(String exceptionString, Exception e) {
        System.err.println(new StringBuilder(exceptionString).append(e.getMessage()));
    }
}
