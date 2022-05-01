package ru.netology.diploma_cloudservice.exceptions;

public class UnauthorizedError extends RuntimeException {
    public UnauthorizedError(String msg) {
        super(msg);
    }
}
