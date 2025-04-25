package com.example.healthyfoodapp;

public class TrackedFoodItem {
    private final FoodItem foodItem;
    private final int quantity;

    public TrackedFoodItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalCalories() {
        return foodItem.getCalories() * quantity;
    }
}
