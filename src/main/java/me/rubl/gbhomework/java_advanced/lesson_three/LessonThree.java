package me.rubl.gbhomework.java_advanced.lesson_three;

import java.util.*;

public class LessonThree {

    public static final String TEXT =
            "Lorem ipsum. Lorem ipsum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut " +
            "enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
            "aliquip ex ea commodo consequat.";

    public static void main(String[] args) {

        HashSet<String> uniqueWords = getUniqueWords(TEXT);
        System.out.println("Unique words:\n" + Arrays.toString(uniqueWords.toArray()));

        System.out.println();

        System.out.println("Word counters:");
        HashMap<String, Integer> wordsMap = getWordsMap(TEXT);

        for (String key : wordsMap.keySet()) {
            System.out.println("\t" + key + ": " + wordsMap.get(key));
        }

        System.out.println();

        PhoneBook phoneBook = PhoneBook.getInstance();
        System.out.println("All persons: " + phoneBook.getAllPersons());
        System.out.println("'Меркулов' phones: " + phoneBook.getPhonesByLastName("Меркулов"));
        System.out.println("'Меркулов' emails: " + phoneBook.getEmailsByLastName("Меркулов"));
        System.out.println("'Муромец' phones: " + phoneBook.getPhonesByLastName("Муромец"));
        System.out.println(phoneBook.addPhone("Муромец", "+79987654321") ? "Phone for 'Муромец' added" : "Phone for 'Муромец' not added");
        System.out.println("'Муромец' phones: " + phoneBook.getPhonesByLastName("Муромец"));
        System.out.println("'Муромец' emails: " + phoneBook.getEmailsByLastName("Муромец"));
        System.out.println(phoneBook.addPhone("Климов", "+79987654321") ? "Phone for 'Климов' added" : "Phone for 'Климов' not added");
        System.out.println(phoneBook.addEmail("Борисов", "borisov@ya.ru") ? "Email for 'Борисов' added" : "Email for 'Борисов' not added");
        System.out.println("All persons: " + phoneBook.getAllPersons());
    }

    static HashSet<String> getUniqueWords(String text) {

        HashSet<String> result = new HashSet<>();

        String[] words = text.split("[^A-Za-z0-9]");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.add(word);
            }
        }

        return result;
    }

    static HashMap<String, Integer> getWordsMap(String text) {

        String[] words = text.split("[^A-Za-z0-9]");

        HashMap<String, Integer> result = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.merge(word, 1, Integer::sum);
            }
        }

        return result;
    }
}
