package chef.service;

import chef.model.Vegetable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Salad {
    private List<Vegetable> vegetables;

    public Salad() {
        this.vegetables = new ArrayList<>();
    }

    public void addVegetable(Vegetable vegetable) {
        vegetables.add(vegetable);
    }

    public double calculateTotalCalories() {
        double totalCalories = 0;
        for (Vegetable vegetable : vegetables) {
            totalCalories += vegetable.getCaloriesPer100g();
        }
        return totalCalories;
    }

    public void sortVegetablesByCalories() {
        vegetables.sort(Comparator.comparingDouble(Vegetable::getCaloriesPer100g));
    }

    public List<Vegetable> findVegetablesByCaloriesRange(double minCalories, double maxCalories) {
        List<Vegetable> result = new ArrayList<>();
        for (Vegetable vegetable : vegetables) {
            if (vegetable.getCaloriesPer100g() >= minCalories && vegetable.getCaloriesPer100g() <= maxCalories) {
                result.add(vegetable);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Салат:\n");
        for (Vegetable vegetable : vegetables) {
            sb.append(vegetable.toString()).append("\n");
        }
        return sb.toString();
    }
}