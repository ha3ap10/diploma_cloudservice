package ru.netology.diploma_cloudservice.repositiry;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Optional;

@Repository
public class FileRepository {

    private static final String DIRECTORY = "media/";

    public void uploadFile(String guid, byte[] file) {

        String path = DIRECTORY + guid;
        try (FileOutputStream os = new FileOutputStream(path);
             BufferedOutputStream bos = new BufferedOutputStream(os)) {
            bos.write(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Optional<byte[]> downloadFile(String guid) {
        Optional<byte[]> file = Optional.empty();
        String path = DIRECTORY + guid;
        try (FileInputStream is = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(is)) {
            file = Optional.of(bis.readAllBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return file;
    }
    public boolean deleteFile(String guid) {
        String path = DIRECTORY + guid;

        File file = new File(path);
        return file.delete();
    }
}
