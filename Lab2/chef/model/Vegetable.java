package chef.model;

public abstract class Vegetable {
    private String name;
    private double caloriesPer100g;

    // Конструктор
    public Vegetable(String name, double caloriesPer100g) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
    }

    // Методы
    public String getName() {
        return name;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    @Override
    public String toString() {
        return name + " (Калории на 100г: " + caloriesPer100g + ")";
    }
}