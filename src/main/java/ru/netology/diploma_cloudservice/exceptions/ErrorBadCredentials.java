package ru.netology.diploma_cloudservice.exceptions;

public class ErrorBadCredentials extends RuntimeException {
    public ErrorBadCredentials(String msg) {
        super(msg);
    }
}
