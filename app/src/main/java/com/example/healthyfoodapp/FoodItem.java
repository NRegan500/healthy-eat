package com.example.healthyfoodapp;

public class FoodItem {
    private final String name;
    private final String ingredients;
    private final int calories;

    public FoodItem(String name, String ingredients, int calories) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }
}
