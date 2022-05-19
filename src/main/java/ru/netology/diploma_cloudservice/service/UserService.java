package ru.netology.diploma_cloudservice.service;

import org.springframework.stereotype.Service;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.exceptions.UnauthorizedError;
import ru.netology.diploma_cloudservice.model.RequestUser;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.repositiry.JpaUserRepository;

import java.util.UUID;

import static ru.netology.diploma_cloudservice.constants.Constants.SUCCESS_LOGOUT;
import static ru.netology.diploma_cloudservice.constants.Constants.UNAUTHORIZED_ERROR;

@Service
public class UserService {

    private JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public Token login(RequestUser user) {
        User findUser = jpaUserRepository.findByLogin(user.getLogin())
                .orElseThrow(() -> new ErrorBadCredentials("Не найден"));

        Token token = new Token(UUID.randomUUID().toString());
        findUser.setAuthToken(token);
        jpaUserRepository.save(findUser);
        return token;
    }

    public String logout(Token authToken) {
        User user = findUser(authToken);
        user.setAuthToken(new Token());
        jpaUserRepository.save(user);
        return SUCCESS_LOGOUT.get();
    }

    private User findUser(Token token) {
        return jpaUserRepository.findByAuthToken(token)
                .orElseThrow(() -> new UnauthorizedError(UNAUTHORIZED_ERROR.get()));
    }

}
