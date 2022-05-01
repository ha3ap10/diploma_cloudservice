package ru.netology.diploma_cloudservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma_cloudservice.exceptions.ErrorBadCredentials;
import ru.netology.diploma_cloudservice.model.ErrorMessage;
import ru.netology.diploma_cloudservice.model.File;
import ru.netology.diploma_cloudservice.model.Token;
import ru.netology.diploma_cloudservice.service.FileService;

import java.io.IOException;
import java.util.List;

import static ru.netology.diploma_cloudservice.constants.Constants.*;

@CrossOrigin(originPatterns = "http://localhost*")
@RestController("/cloud")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestHeader("auth-token") Token token,
                                             @RequestParam("filename") String fileName,
                                             @RequestBody MultipartFile file) throws IOException {
        if (token.getToken().startsWith("Bearer ")) {
            token.setToken(token.getToken().substring(7));
        }
        System.out.println(file.getSize());
        return new ResponseEntity<>(fileService.uploadFile(token, fileName, file),HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public String deleteFile(@RequestHeader("auth-token") Token token,
                             @RequestParam("filename") String fileName) {
        return SUCCESS_DELETED.get();
    }

    @GetMapping("/file")
    public String downloadFile() {
        return SUCCESS_DOWNLOAD.get();
    }

    @PutMapping("/file")
    public String editFile() {
        return SUCCESS_UPLOAD.get();
    }

    @GetMapping("/list")
    public List<File> getFilesList(@RequestHeader("auth-token") Token token,
                                   @RequestParam("limit") Integer limit) {
        System.out.println("FileController/list: " + token);
        if (token.getToken().startsWith("Bearer ")) {
            token.setToken(token.getToken().substring(7));
        }
        return fileService.getFilesList(token, limit);
    }

    @ExceptionHandler(ErrorBadCredentials.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage errorMessage(ErrorBadCredentials exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
