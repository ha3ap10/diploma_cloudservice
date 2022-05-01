package ru.netology.diploma_cloudservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class File {

    @JsonProperty("filename")
    @Column(nullable = false, length = 50)
    private String fileName;

    @Column(nullable = false)
    private Long size;
}
