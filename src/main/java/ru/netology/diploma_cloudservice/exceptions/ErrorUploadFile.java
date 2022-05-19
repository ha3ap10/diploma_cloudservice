package ru.netology.diploma_cloudservice.exceptions;

public class ErrorUploadFile extends RuntimeException {
    public ErrorUploadFile(String msg) {
        super(msg);
    }
}
