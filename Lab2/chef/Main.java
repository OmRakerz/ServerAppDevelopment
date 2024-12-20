package chef;

import chef.model.FruitVegetable;
import chef.model.LeafyGreen;
import chef.model.RootVegetable;
import chef.model.Vegetable;
import chef.service.Salad;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Salad salad = new Salad();
        initializeSalad(salad);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n1. Покажи салат");
            System.out.println("2. Рассчитать общее количество калорий");
            System.out.println("3. Сортировка овощей по калориям");
            System.out.println("4. Сортировать овощи по калорийности");
            System.out.println("5. Выход");
            System.out.print("Выберите номер: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println(salad);
                    break;
                case 2:
                    System.out.println("Общее количество калорий: " + salad.calculateTotalCalories());
                    break;
                case 3:
                    salad.sortVegetablesByCalories();
                    System.out.println("Овощи отсортированы по калорийности.");
                    break;
                case 4:
                    System.out.print("Введите мин. калории: ");
                    double minCalories = scanner.nextDouble();
                    System.out.print("Введите макс. калории: ");
                    double maxCalories = scanner.nextDouble();
                    scanner.nextLine(); // consume newline

                    List<Vegetable> foundVegetables = salad.findVegetablesByCaloriesRange(minCalories, maxCalories);
                    System.out.println("Найдено овощей:");
                    foundVegetables.forEach(System.out::println);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный вариант. Попробуйте еще раз.");
            }
        }

        scanner.close();
    }

    private static void initializeSalad(Salad salad) {
        Properties properties = new Properties();
        String filePath = "C:/Users/Home/Desktop/Lab2/config/vegetables.properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);

            for (String key : properties.stringPropertyNames()) {
                String[] parts = properties.getProperty(key).split(",");
                String type = parts[0];
                double calories = Double.parseDouble(parts[1]);

                Vegetable vegetable;
                switch (type) {
                    case "LeafyGreen":
                        vegetable = new LeafyGreen(key, calories);
                        break;
                    case "RootVegetable":
                        vegetable = new RootVegetable(key, calories);
                        break;
                    case "FruitVegetable":
                        vegetable = new FruitVegetable(key, calories);
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный вид овощей: " + type);
                }
                salad.addVegetable(vegetable);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла свойств: " + e.getMessage());
        }
    }
}