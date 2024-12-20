import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Abiturient {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String address;
    private String phone;
    private List<Integer> grades;

    // Конструкторы
    public Abiturient(int id, String lastName, String firstName, String middleName, String address, String phone, List<Integer> grades) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.phone = phone;
        this.grades = grades;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    // Метод для получения суммы оценок
    public int getTotalGrades() {
        int sum = 0;
        for (Integer grade : grades) {
            sum += grade;
        }
        return sum;
    }

    // Переопределение методов toString() и hashCode()
    @Override
    public String toString() {
        return "Abiturient{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", grades=" + grades +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Abiturient that = (Abiturient) obj;
        return id == that.id;
    }
}

class AbiturientManager {
    private final List<Abiturient> abiturients;

    public AbiturientManager(List<Abiturient> abiturients) {
        this.abiturients = abiturients;
    }

    // Метод для вывода списка абитуриентов с неудовлетворительными оценками
    public void printAbiturientsWithUnsatisfactoryGrades() {
        for (Abiturient abiturient : abiturients) {
            for (Integer grade : abiturient.getGrades()) {
                if (grade < 3) {
                    System.out.println(abiturient);
                    break; // Прерываем цикл, если найдена неудовлетворительная оценка
                }
            }
        }
    }

    // Метод для вывода списка абитуриентов с суммой баллов выше заданной
    public void printAbiturientsWithTotalGradesAbove(int threshold) {
        for (Abiturient abiturient : abiturients) {
            if (abiturient.getTotalGrades() > threshold) {
                System.out.println(abiturient);
            }
        }
    }

    // Метод для выбора заданного числа n абитуриентов с самой высокой суммой баллов
    public void printTopNAbiturients(int n) {
        // Создаем копию списка для сортировки
        List<Abiturient> sortedAbiturients = new ArrayList<>(abiturients);

        // Сортируем список вручную
        sortedAbiturients.sort((a1, a2) -> {
            int sum1 = a1.getTotalGrades();
            int sum2 = a2.getTotalGrades();
            return Integer.compare(sum2, sum1); // Сортировка по убыванию
        });

        // Выводим топ-n абитуриентов
        for (int i = 0; i < n && i < sortedAbiturients.size(); i++) {
            System.out.println(sortedAbiturients.get(i));
        }
    }

    // Метод для вывода полного списка абитуриентов с полупроходной суммой
    public void printAbiturientsWithThresholdGrades(int threshold) {
        for (Abiturient abiturient : abiturients) {
            if (abiturient.getTotalGrades() == threshold) {
                System.out.println(abiturient);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<Abiturient> abiturients = new ArrayList<>();
        abiturients.add(new Abiturient(1, "Иванов", "Иван", "Иванович", "ул. Ленина, 1", "123-45-67", List.of(5, 4, 3, 2)));
        abiturients.add(new Abiturient(2, "Петров", "Петр", "Петрович", "ул. Гагарина, 2", "234-56-78", List.of(5, 5, 5, 5)));
        abiturients.add(new Abiturient(3, "Сидоров", "Сидор", "Сидорович", "ул. Пушкина, 3", "345-67-89", List.of(4, 4, 4, 4)));
        abiturients.add(new Abiturient(4, "Кузнецов", "Кузя", "Кузьмич", "ул. Гоголя, 4", "456-78-90", List.of(3, 3, 3, 3)));
        abiturients.add(new Abiturient(5, "Васильев", "Вася", "Васильевич", "ул. Толстого, 5", "567-89-01", List.of(2, 2, 2, 2)));

        AbiturientManager manager = new AbiturientManager(abiturients);

        System.out.println("Список абитуриентов с неудовлетворительными оценками:");
        manager.printAbiturientsWithUnsatisfactoryGrades();

        System.out.println("\nСписок абитуриентов с суммой баллов выше 15:");
        manager.printAbiturientsWithTotalGradesAbove(15);

        System.out.println("\nТоп 3 абитуриентов с самой высокой суммой баллов:");
        manager.printTopNAbiturients(3);

        System.out.println("\nСписок абитуриентов с полупроходной суммой баллов (16):");
        manager.printAbiturientsWithThresholdGrades(16);
    }
}