package ru.netology.diploma_cloudservice.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.exceptions.ErrorInputData;
import ru.netology.diploma_cloudservice.exceptions.UnauthorizedError;
import ru.netology.diploma_cloudservice.model.File;
import ru.netology.diploma_cloudservice.model.Files;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.model.User;
import ru.netology.diploma_cloudservice.repositiry.FileRepository;
import ru.netology.diploma_cloudservice.repositiry.JpaFileRepository;
import ru.netology.diploma_cloudservice.repositiry.JpaUserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.netology.diploma_cloudservice.constants.Constants.*;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final JpaFileRepository jpaFileRepository;
    private final JpaUserRepository jpaUserRepository;

    public FileService(FileRepository fileRepository, JpaFileRepository jpaFileRepository, JpaUserRepository jpaUserRepository) {
        this.fileRepository = fileRepository;
        this.jpaFileRepository = jpaFileRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    public String uploadFile(Token token, String fileName, MultipartFile file) throws IOException {

        User user = findUser(token);

        if (jpaFileRepository.findByFile_FileNameAndUsername(fileName, user.getUsername()).isEmpty()) {
            Date date = new Date();
            String guid = UUID.nameUUIDFromBytes(file.getBytes()).toString();
            File fileInfo = new File(fileName, file.getSize());

            Files uploadedFile = new Files();
            uploadedFile.setDate(date);
            uploadedFile.setGuid(guid);
            uploadedFile.setUsername(user.getUsername());
            uploadedFile.setFile(fileInfo);

            jpaFileRepository.save(uploadedFile);
            fileRepository.uploadFile(guid, file.getBytes());

            return SUCCESS_UPLOAD.get();
        } else {
            throw new ErrorBadCredentials(ERROR_INPUT_DATA.get());
        }
    }

    public List<File> getFilesList(Token token, Integer limit) {
        return jpaFileRepository.findFileByUsername(findUser(token).getUsername());
    }

    public String deleteFile(Token token, String fileName) {
        Files searched = jpaFileRepository.findByFile_FileNameAndUsername(fileName, findUser(token).getUsername())
                .orElseThrow(() -> new ErrorInputData(ERROR_INPUT_DATA.get()));

        if (fileRepository.deleteFile(searched.getGuid())) {
            jpaFileRepository.delete(searched);
            return SUCCESS_DELETED.get();
        } else {
            return ERROR_DELETE_FILE.get();
        }
    }

    public Resource downloadFile(Token token, String fileName) {
        Files removableFile = jpaFileRepository
                .findByFile_FileNameAndUsername(fileName, findUser(token).getUsername())
                .orElseThrow(() -> new ErrorInputData(ERROR_INPUT_DATA.get()));
        return fileRepository.downloadFile(removableFile.getGuid());
    }

    private User findUser(Token token) {
        return jpaUserRepository.findByAuthToken(token)
                .orElseThrow(() -> new UnauthorizedError(UNAUTHORIZED_ERROR.get()));
    }

    public String editFile(Token token, String fileName, Map<String, String> newFileName) {
        Files editableFile = jpaFileRepository
                .findByFile_FileNameAndUsername(fileName, findUser(token).getUsername())
                .orElseThrow(() -> new ErrorInputData(ERROR_INPUT_DATA.get()));
        jpaFileRepository.editFile(newFileName.get(FILENAME.get()), editableFile.getId());
        return SUCCESS_EDIT.get();
    }
}
