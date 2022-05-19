package ru.netology.diploma_cloudservice.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User,Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByAuthToken(Token authToken);

}