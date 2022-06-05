package ru.netology.diploma_cloudservice.controller;

import ru.netology.diploma_cloudservice.model.Token;

import static ru.netology.diploma_cloudservice.constants.Constants.TOKEN_START;

public class CommonControllerUtil {
    protected Token updateToken(Token token) {
        if (token.getToken().startsWith(TOKEN_START.get())) {
            token.setToken(token.getToken().split(" ")[1].trim());
        }
        return token;
    }
}
