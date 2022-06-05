package ru.netology.diploma_cloudservice.constants;

public enum Constants {

    AUTH_TOKEN("auth-token"),
    BAD_CREDENTIALS("Bad credentials"),
    ERROR_DELETE_FILE("Error delete file"),
    ERROR_DOWNLOAD_FILE("Error download file"),
    ERROR_GETTING_FILE_LIST("Error getting file list"),
    ERROR_INPUT_DATA("Error input data"),
    FILENAME("filename"),
    SUCCESS_AUTHORIZATION("Success authorization"),
    SUCCESS_DELETED("Success deleted"),
    SUCCESS_DOWNLOAD("Success download"),
    SUCCESS_EDIT("Success edit"),
    SUCCESS_GET_LIST("Success get list"),
    SUCCESS_LOGOUT("Success logout"),
    SUCCESS_UPLOAD("Success upload"),
    TOKEN_START("Bearer "),
    UNAUTHORIZED_ERROR("Unauthorized error"),
    USER_LOGIN("User %s login"),
    USER_LOGOUT("User %s logout"),
    USER_NOT_FOUND("User not found");

    private String title;

    Constants(String title) {
        this.title = title;
    }

    public String get() {
        return title;
    }
}
