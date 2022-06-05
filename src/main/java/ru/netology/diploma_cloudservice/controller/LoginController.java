package ru.netology.diploma_cloudservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.netology.diploma_cloudservice.configuration.security.JwtTokenUtil;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.model.ErrorMessage;
import ru.netology.diploma_cloudservice.model.RequestUser;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.service.JwtUserDetailService;

import javax.validation.Valid;

@AllArgsConstructor
@CrossOrigin(originPatterns = "http://localhost*")
@RestController
public class LoginController extends CommonControllerUtil {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody RequestUser requestUser) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestUser.getLogin(), requestUser.getPassword()));

        User user = (User) authenticate.getPrincipal();

        user.setAuthToken(new Token(jwtTokenUtil.generateAccessToken(user)));

        User userToUpdate = (User) jwtUserDetailService.loadUserByUsername(user.getUsername());
        jwtUserDetailService.saveUser(userToUpdate);

        return new ResponseEntity<>(user.getAuthToken(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") Token authToken) {
        return new ResponseEntity<>(jwtUserDetailService.logout(updateToken(authToken)), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> logoutGet(@RequestHeader("auth-token") Token authToken) {
        return new ResponseEntity<>(jwtUserDetailService.logout(updateToken(authToken)), HttpStatus.OK);
    }

    @ExceptionHandler(ErrorBadCredentials.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage errorMessage(ErrorBadCredentials exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
