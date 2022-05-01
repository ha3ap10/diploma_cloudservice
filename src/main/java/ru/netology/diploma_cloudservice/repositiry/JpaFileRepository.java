package ru.netology.diploma_cloudservice.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.netology.diploma_cloudservice.model.File;
import ru.netology.diploma_cloudservice.model.Files;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFileRepository extends JpaRepository<Files, Long> {

    @Query("select f.file from Files f where f.login = ?1")
    List<File> findFileByLogin(String login);

    Optional<Files> findByFile_FileNameAndLogin(String fileName, String login);

}
