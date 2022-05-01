package ru.netology.diploma_cloudservice.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.exceptions.UnauthorizedError;
import ru.netology.diploma_cloudservice.model.File;
import ru.netology.diploma_cloudservice.model.Files;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.repositiry.FileRepository;
import ru.netology.diploma_cloudservice.repositiry.JpaFileRepository;
import ru.netology.diploma_cloudservice.repositiry.UserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ru.netology.diploma_cloudservice.constants.Constants.*;

@Service
public class FileService {

    private FileRepository fileRepository;
    private JpaFileRepository jpaFileRepository;
    private UserRepository userRepository;

    public FileService(FileRepository fileRepository, JpaFileRepository jpaFileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.jpaFileRepository = jpaFileRepository;
        this.userRepository = userRepository;
    }

    public String uploadFile(Token token, String fileName, MultipartFile file) throws IOException {
        System.out.println(token);
        User user = userRepository.findByAuthToken(token)
                .orElseThrow(() -> new UnauthorizedError(UNAUTHORIZED_ERROR.get()));

        if (jpaFileRepository.findByFile_FileNameAndLogin(fileName, user.getLogin()).isEmpty()) {
            Date date = new Date();
            String guid = UUID.nameUUIDFromBytes(file.getBytes()).toString();
            File fileInfo = new File(fileName, file.getSize());

            Files uploadedFile = new Files();
            uploadedFile.setDate(date);
            uploadedFile.setGuid(guid);
            uploadedFile.setLogin(user.getLogin());
            uploadedFile.setFile(fileInfo);

            jpaFileRepository.save(uploadedFile);
            fileRepository.uploadFile(guid, file.getBytes());

            return SUCCESS_UPLOAD.get();
        } else {
            throw new ErrorBadCredentials(ERROR_INPUT_DATA.get());
        }
    }

    public List<File> getFilesList(Token token, Integer limit) {
        User user = userRepository.findByAuthToken(token)
                .orElseThrow(() -> new UnauthorizedError(UNAUTHORIZED_ERROR.get()));
        return jpaFileRepository.findFileByLogin(user.getLogin());
    }
}
