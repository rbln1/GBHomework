package me.rubl.gbhomework.java_advanced.lesson_three;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {

    private static PhoneBook instance;

    private HashMap<String, Person> mainMap = new HashMap<>();

    private PhoneBook() {
        makeMockPhoneBook();
    }

    public static PhoneBook getInstance() {
        if (instance == null) {
            instance = new PhoneBook();
        }
        return instance;
    }

    private void makeMockPhoneBook() {

        String[] lastNames = {"Иванов", "Петров", "Сидоров", "Муромец", "Меркулов"};

        for (int i = 0; i < lastNames.length; i++) {
            Person person = new Person(lastNames[i]);
            person.addPhone("+79123456789");

            for (int j = 0; j < i; j++) {
                person.addEmail("example" + j + "@mail.ru");
            }

            mainMap.put(lastNames[i], person);
        }
    }

    public boolean addPhone(String lastName, String phone) {
        Person person = mainMap.get(lastName);
        if (person == null) {
            person = new Person(lastName);
            mainMap.put(lastName, person);
        }

        return person.addPhone(phone);
    }

    public boolean addEmail(String lastName, String email) {
        Person person = mainMap.get(lastName);
        if (person == null) {
            person = new Person(lastName);
            mainMap.put(lastName, person);
        }

        return person.addEmail(email);
    }

    public ArrayList<String> getPhonesByLastName(String lastName) {
        Person person = mainMap.get(lastName);
        if (person != null) {
            return person.getAllPhones();
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<String> getEmailsByLastName(String lastName) {
        Person person = mainMap.get(lastName);
        if (person != null) {
            return person.getAllEmails();
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<Person> getAllPersons() {

        ArrayList<Person> result = new ArrayList<>();

        if (!mainMap.isEmpty()) {
            for (String key : mainMap.keySet()) {
                result.add(mainMap.get(key));
            }
        }

        return result;
    }
}
