package ru.netology.diploma_cloudservice.service;

import org.springframework.stereotype.Service;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.model.RequestUser;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.repositiry.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Token login(RequestUser user) {
         User findUser = userRepository.findByLogin(user.getLogin())
                 .orElseThrow(() -> new ErrorBadCredentials("Не найден"));

        System.out.println("UserService: " + findUser.getAuthToken());
         return new Token(findUser.getAuthToken());
    }

    public String logout(String authToken) {
        return "hello";
    }
}
