package ru.netology.diploma_cloudservice.exceptions;

public class ErrorInputData extends RuntimeException {
    public ErrorInputData(String msg) {
        super(msg);
    }
}
