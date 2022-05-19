package ru.netology.diploma_cloudservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.model.ErrorMessage;
import ru.netology.diploma_cloudservice.model.RequestUser;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.service.UserService;

import javax.validation.Valid;

@CrossOrigin(originPatterns = "http://localhost*")
@RestController
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody RequestUser user) {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") Token authToken) {
        return new ResponseEntity<>(userService.logout(updateToken(authToken)), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> logoutGet(@RequestHeader("auth-token") Token authToken) {
        return new ResponseEntity<>(userService.logout(updateToken(authToken)), HttpStatus.OK);
    }

    @ExceptionHandler(ErrorBadCredentials.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage errorMessage(ErrorBadCredentials exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }

    private Token updateToken(Token token) {
        if (token.getToken().startsWith("Bearer ")) {
            token.setToken(token.getToken().substring(7));
        }
        return token;
    }

}
