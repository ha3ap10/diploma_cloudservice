package ru.netology.diploma_cloudservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.exceptions.UnauthorizedError;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.repositiry.JpaUserRepository;

import static ru.netology.diploma_cloudservice.constants.Constants.*;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return jpaUserRepository.findByUsername(username)
                .orElseThrow(() -> new ErrorBadCredentials(USER_NOT_FOUND.get()));
    }

    public void saveUser(User user) {
        jpaUserRepository.save(user);
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
