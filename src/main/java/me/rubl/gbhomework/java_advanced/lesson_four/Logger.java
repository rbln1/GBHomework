package me.rubl.gbhomework.java_advanced.lesson_four;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logger {

    public enum LogType {
        SENT_MESSAGE,
        COMMON,
        ERROR
    }

    private static Logger instance;

    private FileOutputStream logsOutputStream;
    private DateFormat logDateFormatter;

    private Logger() {
        openOutputStream();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void addToLogFile(String message, LogType type) {

        if (logsOutputStream == null) {
            openOutputStream();
            addToLogFile(message, type);
        } else {
            String dateFormatted = getLogDateFormatter().format(new Date(System.currentTimeMillis()));

            try {
                switch (type) {
                    case SENT_MESSAGE:
                        logsOutputStream.write(String.format("[%s] %s | \"%s\"\n", dateFormatted, type.toString(), message).getBytes());
                        break;
                    case COMMON:
                        logsOutputStream.write(String.format("[%s] %s | %s\n", dateFormatted, type.toString(), message).getBytes());
                        break;
                    default:break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                addToLogFile(message, type);
            }
        }
    }

    public void addToLogFile(Exception e){
        StackTraceElement[] ste = e.getStackTrace();
        String msg = "Exception in " + Thread.currentThread().getName() + " " +
                e.getClass().getCanonicalName() + ": \"" +
                e.getMessage() + "\"\n\t at " + ste[0];
        addToLogFile(msg, LogType.ERROR);
    }

    private DateFormat getLogDateFormatter() {
        if (logDateFormatter == null) {
            logDateFormatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
            logDateFormatter.setTimeZone(Calendar.getInstance().getTimeZone());
        }

        return logDateFormatter;
    }

    private void openOutputStream() {

        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());

        File logFile = new File(String.format("logs_%s.txt", dateFormat.format(new Date(System.currentTimeMillis()))));

        if (logsOutputStream == null) {
            try {
                logsOutputStream = new FileOutputStream(logFile, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                closeOutputStream();
            }
        }
    }

    private void closeOutputStream() {
        if (logsOutputStream != null) {
            try {
                logsOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
