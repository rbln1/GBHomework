package me.rubl.gbhomework.java_base.lesson_six;

import java.io.*;
import java.util.Scanner;

public class LessonSix {

    /*
        + 1. Создать 2 текстовых файла, примерно по 50-100 символов в каждом(особого значения не имеет);
        + 2. Написать метод, «склеивающий» эти файлы,
            то есть вначале идет текст из первого файла, потом текст из второго.
        + 3*. Написать метод, который проверяет присутствует ли
            указанное пользователем слово в файле (работаем только с латиницей).
        + 4**. Написать метод, проверяющий, есть ли указанное слово в папке
     */

    public static void main(String[] args){

        // -- 1 --
        System.out.println("[1] Making two files: \"Lorem Ipsum\"");

        boolean resultOfMaking = makeTwoFiles();
        if (!resultOfMaking) {
            System.out.println("Error while making files.\n");
            return;
        } else System.out.println("Making successfully.\n");

        // -- 2 --
        System.out.println("[2] Concatenating files...");

        boolean resultOfConcat = concatFiles(
                new File("lesson_six_file_1.txt"),
                new File("lesson_six_file_2.txt")
        );
        if (resultOfConcat) System.out.println("Files concatenated successfully!\n");
        else {
            System.out.println("Error while concatenating files.\n");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // -- 3 --
        System.out.print("[3] Enter a search word for search in file: ");

        String searchFileWord = scanner.next();
        boolean resultSearchFile = isFileContainsWord(
                new File("lesson_six_file_1.txt"),
                searchFileWord
        );
        if (resultSearchFile) System.out.printf("Word \"%s\" was found.\n", searchFileWord);
        else System.out.printf("Word \"%s\" not found.\n", searchFileWord);

        // -- 4 --
        System.out.print("[4] Enter a search word for search in folder: ");

        String searchFolderWord = scanner.next();
        boolean searchFolderResult = isFolderContainsWord(
                new File("./"),
                searchFolderWord
        );
        if (searchFolderResult) System.out.printf("Word \"%s\" was found.\n", searchFolderWord);
        else System.out.printf("Word \"%s\" not found.\n", searchFolderWord);
    }

    static boolean makeTwoFiles() {

        String firstText =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna " +
                "aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "GeekBrains.";
        String secondText =
                "Duis aute irure dolor in reprehenderit in voluptate velit " +
                "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.";

        try {
            FileOutputStream fileOneOutputStream = new FileOutputStream("lesson_six_file_1.txt", false);
            fileOneOutputStream.write(firstText.getBytes());
            fileOneOutputStream.close();

            FileOutputStream fileTwoOutputStream = new FileOutputStream("lesson_six_file_2.txt", false);
            fileTwoOutputStream.write(secondText.getBytes());
            fileTwoOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static boolean concatFiles(File ... files) {

        try {
            FileOutputStream outputStream =
                    new FileOutputStream("lesson_six_file_" + (files.length + 1) + ".txt", false);

            for (File file : files) {
                FileInputStream inputStream = new FileInputStream(file);
                int buf;
                while ((buf = inputStream.read()) != -1) {
                    outputStream.write(buf);
                }
                outputStream.write('\n');
                inputStream.close();
            }

            outputStream.close();
            return true;
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
        }
        return false;
    }

    static boolean isFileContainsWord(File file, String search_word) {

        if (file == null || !file.isFile() || search_word == null || search_word.isEmpty()) return false;

        boolean searchResult = false;

        try {
            FileInputStream inputStream = new FileInputStream(file);

            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNext()) {
                if (scanner.next().toLowerCase().contains(search_word.toLowerCase())) {
                    searchResult = true;
                    break;
                }
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResult;
    }

    static boolean isFolderContainsWord(File folder, String search_word) {

        if (folder != null && folder.isDirectory()) {
            File[] filesInFolder = folder.listFiles();
            if (filesInFolder == null || filesInFolder.length == 0) return false;
            for (File file : filesInFolder) {
                if (isFileContainsWord(file, search_word)){
                    return true;
                }
            }
        }

        return false;
    }

}
