package me.rubl.gbhomework.java_base.lesson_four;

// 1. Создать класс "Сотрудник" с полями: ФИО, зарплата, возраст;
public class Employee {

    // 8. *** Продумать конструктор таким образом,
    //        чтобы при создании каждому сотруднику присваивался
    //        личный уникальный идентификационный порядковый номер
    public static int empCount = 1;

    private final int id;
    private String commonName;
    private int salary;
    private int age;

    // 2. Конструктор класса должен заполнять эти поля (ФИО, зарплата, возраст) при создании объекта;
    public Employee(String commonName, int salary, int age) {

        // 8. *** Продумать конструктор таким образом,
        //        чтобы при создании каждому сотруднику присваивался
        //        личный уникальный идентификационный порядковый номер
        this.id = empCount++;

        this.commonName = commonName;
        this.salary = salary;
        this.age = age;
    }

    // 3. Внутри класса «Сотрудник» написать методы, которые возвращают значение каждого поля (ФИО, зарплата, возраст);

    public int getId() {
        return id;
    }

    public String getCommonName() {
        return commonName;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    // 7. * Подсчитать средние арифметические зарплаты и возраста
    public static double averageSalary(Employee[] employees) {

        int summary = 0;

        for (Employee employee : employees) {
            summary += employee.getSalary();
        }

        return summary * 1.0 / employees.length;
    }

    public static int averageAge(Employee[] employees) {

        int summary = 0;

        for (Employee employee : employees) {
            summary += employee.getAge();
        }

        return summary / employees.length;
    }

    // 6. * Создать метод, повышающий зарплату всем сотрудникам старше 45 лет на 5000.
    public static void increaseSalary(Employee[] employees) {

        for (Employee employee : employees) {
            if (employee.getAge() >= 45) {
                int oldSalary = employee.getSalary();
                employee.salary = oldSalary + 5000;
            }
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "commonName='" + commonName + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
