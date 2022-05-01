package ru.netology.diploma_cloudservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Embedded
    private Token authToken;

    @Id
    private String login;

    @Column(nullable = false, length = 50)
    private String password;

    public String getAuthToken() {
        return authToken.getToken();
    }
}