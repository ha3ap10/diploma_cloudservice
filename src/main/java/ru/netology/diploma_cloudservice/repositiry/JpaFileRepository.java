package ru.netology.diploma_cloudservice.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diploma_cloudservice.model.File;
import ru.netology.diploma_cloudservice.model.Files;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface JpaFileRepository extends JpaRepository<Files, Long> {

    @Query("select f.file from Files f where f.username = ?1")
    List<File> findFileByUsername(String username);

    Optional<Files> findByFile_FileNameAndUsername(String fileName, String username);

    @Modifying
    @Query("update Files f set f.file.fileName = ?1 where f.id = ?2")
    void editFile(String newFileName, Long id);

}
