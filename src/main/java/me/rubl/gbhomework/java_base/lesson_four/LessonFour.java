package me.rubl.gbhomework.java_base.lesson_four;

import java.util.Arrays;

public class LessonFour {

    /*
    1. Создать класс "Сотрудник" с полями: ФИО, зарплата, возраст;
    2. Конструктор класса должен заполнять эти поля при создании объекта;
    3. Внутри класса «Сотрудник» написать методы, которые возвращают значение каждого поля;
    4. Вывести при помощи методов из пункта 3 ФИО и возраст.
    5. Создать массив из 5 сотрудников. С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
    6. * Создать метод, повышающий зарплату всем сотрудникам старше 45 лет на 5000.
    7. * Подсчитать средние арифметические зарплаты и возраста
    8. *** Продумать конструктор таким образом, чтобы при создании каждому сотруднику присваивался личный
           уникальный идентификационный порядковый номер
    */

    public static void main(String[] args) {

        // 5. Создать массив из 5 сотрудников. С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
        Employee[] employees = {
                new Employee("Andrey Olegovich Dolgushin", 85000, 24),
                new Employee("Boris Borisovich Borisov", 200000, 42),
                new Employee("Andrey Nikolaevich Slipchenko", 90000, 51),
                new Employee("Dmitriy Mihaylovich Leyning", 120000, 55),
                new Employee("Ohrimenko Zhanna Valerievna", 110000, 30)
        };

        // 4. Вывести при помощи методов из пункта 3 ФИО и возраст.
        for (Employee employee : employees) {
            System.out.println(
                employee.getId() + ": " +
                employee.getCommonName() + ", " +
                employee.getAge() + " y.o."
            );
        }

        // 5. Создать массив из 5 сотрудников. С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
        System.out.println("\nOver 40 years old employees:");
        for (Employee employee : employees) {
            if (employee.getAge() >= 40) {
                System.out.println(
                    employee.getId() + ": " +
                    employee.getCommonName() + ", " +
                    employee.getAge() + " y.o."
                );
            }
        }

        // 6. * Создать метод, повышающий зарплату всем сотрудникам старше 45 лет на 5000.
        System.out.println("\nSalary increase for 45+");
        Employee.increaseSalary(employees);

        // 7. * Подсчитать средние арифметические зарплаты и возраста
        System.out.println("\nAverage salary: " + Employee.averageSalary(employees));
        System.out.println("\nAverage age: " + Employee.averageAge(employees));
    }

}
