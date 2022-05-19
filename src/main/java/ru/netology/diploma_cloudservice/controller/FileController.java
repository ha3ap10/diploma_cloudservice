package ru.netology.diploma_cloudservice.controller;

import org.springframework.core.io.Resource;
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
import java.util.Map;

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
        return new ResponseEntity<>(fileService.uploadFile(updateToken(token), fileName, file),HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public String deleteFile(@RequestHeader("auth-token") Token token,
                             @RequestParam("filename") String fileName) {
        return fileService.deleteFile(updateToken(token), fileName);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("auth-token") Token token,
                                                 @RequestParam("filename") String fileName) {

        Resource resource = fileService.downloadFile(updateToken(token), fileName);
        return new ResponseEntity<>(resource,HttpStatus.OK);
    }

    @PutMapping("/file")
    public String editFile(@RequestHeader("auth-token") Token token,
                           @RequestParam("filename") String fileName,
                           @RequestBody Map<String, String> newFileName) {
        return fileService.editFile(updateToken(token), fileName, newFileName);
    }

    @GetMapping("/list")
    public List<File> getFilesList(@RequestHeader("auth-token") Token token,
                                   @RequestParam("limit") Integer limit) {
        return fileService.getFilesList(updateToken(token), limit);
    }

    @ExceptionHandler(ErrorBadCredentials.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage errorMessage(ErrorBadCredentials exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }

    private Token updateToken(Token token) {
        if (token.getToken().startsWith("Bearer ")) {
            token.setToken(token.getToken().substring(7));
        }
        return token;
    }
}
