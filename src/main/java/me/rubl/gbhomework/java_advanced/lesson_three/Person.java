package me.rubl.gbhomework.java_advanced.lesson_three;

import java.util.ArrayList;

public class Person {

    public static final String PHONE_REGEX = "^((\\+7|7|8)+([0-9]){10})$";
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private String lastName;

    /** Only russian numbers*/
    private ArrayList<String> phones = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();

    public Person(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<String> getAllPhones() {
        return phones;
    }

    public ArrayList<String> getAllEmails() {
        return emails;
    }

    public boolean addPhone(String phone) {
        if (phone.matches(PHONE_REGEX) && !phones.contains(phone)) {
            phones.add(phone);
            return true;
        }
        return false;
    }

    public boolean addEmail(String email) {
        if (email.matches(EMAIL_REGEX) && !emails.contains(email)) {
            emails.add(email);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{\"Last name\": \"%s\", \"phones\": %d, \"emails\": %d}",
                getLastName(), getAllPhones().size(), getAllEmails().size());
    }
}
