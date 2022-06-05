package ru.netology.diploma_cloudservice.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static Logger logger;
    private static final String LOG_FILE_NAME = "file.log";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) logger = new Logger();
        return logger;
    }

    public void info(String msg) {
        String date = SIMPLE_DATE_FORMAT.format(new Date());
        try (FileWriter fw = new FileWriter(LOG_FILE_NAME, true)) {
            fw.write(date + " INFO: " + msg + "\n");
            fw.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void error(String msg) {
        String date = SIMPLE_DATE_FORMAT.format(new Date());
        try (FileWriter fw = new FileWriter(LOG_FILE_NAME, true)) {
            fw.write(date + " ERROR: " + msg + "\n");
            fw.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
