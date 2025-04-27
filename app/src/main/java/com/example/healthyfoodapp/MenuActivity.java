package com.example.healthyfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private List<TrackedFoodItem> trackedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Setup toolbar and hide default title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize activity components
        loadTrackedItems();  // Load previously tracked items from storage

        // Setup each menu section with its respective food items
        setupMenuSection(R.id.starters_recycler, getStarters());
        setupMenuSection(R.id.mains_recycler, getMains());
        setupMenuSection(R.id.desserts_recycler, getDesserts());
        setupMenuSection(R.id.drinks_recycler, getDrinks());
    }

    // Configures a RecyclerView to display a horizontal list of menu items
    private void setupMenuSection(int recyclerViewId, List<FoodItem> items) {
        RecyclerView recyclerView = findViewById(recyclerViewId);
        MenuItemAdapter adapter = new MenuItemAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    // return list of starter menu items

    private List<FoodItem> getStarters() {
        List<FoodItem> items = new ArrayList<>();
        items.add(new FoodItem("Avocado Toast", "Avocado, Wholegrain bread, Cherry tomatoes", 320));
        items.add(new FoodItem("Greek Salad", "Cucumber, Feta, Olives", 280));
        items.add(new FoodItem("Edamame Beans", "Edamame, Sea salt, Lemon", 180));
        items.add(new FoodItem("Quinoa Salad", "Quinoa, Kale, Pomegranate", 250));
        return items;
    }

    // return list of main course items
    private List<FoodItem> getMains() {
        List<FoodItem> items = new ArrayList<>();
        items.add(new FoodItem("Grilled Salmon", "Salmon, Asparagus, Lemon", 420));
        items.add(new FoodItem("Veggie Burger", "Black beans, Sweet potato, Wholegrain bun", 380));
        items.add(new FoodItem("Chicken Buddha Bowl", "Chicken, Quinoa, Avocado", 450));
        items.add(new FoodItem("Mushroom Risotto", "Mushrooms, Arborio rice, Parmesan", 390));
        return items;
    }

    // return list of dessert items
    private List<FoodItem> getDesserts() {
        List<FoodItem> items = new ArrayList<>();
        items.add(new FoodItem("Chia Pudding", "Chia seeds, Almond milk, Berries", 220));
        items.add(new FoodItem("Dark Chocolate Mousse", "Dark chocolate, Avocado, Honey", 280));
        items.add(new FoodItem("Fruit Salad", "Seasonal fruits, Mint, Lime", 150));
        items.add(new FoodItem("Vegan Cheesecake", "Cashews, Dates, Coconut", 310));
        return items;
    }

    // return list of drink items
    private List<FoodItem> getDrinks() {
        List<FoodItem> items = new ArrayList<>();
        items.add(new FoodItem("Green Detox", "Kale, Apple, Ginger", 120));
        items.add(new FoodItem("Berry Smoothie", "Mixed berries, Yogurt, Almond milk", 180));
        items.add(new FoodItem("Turmeric Latte", "Turmeric, Almond milk, Cinnamon", 90));
        items.add(new FoodItem("Cold Brew Coffee", "Coffee beans, Water, Ice", 50));
        return items;
    }

    // Loads tracked food items from SharedPreferences storage
    private void loadTrackedItems() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String json = prefs.getString("trackedItems", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<TrackedFoodItem>>(){}.getType();
            trackedItems = gson.fromJson(json, type);
        }
    }

    // Saves current tracked items to SharedPreferences storage
    private void saveTrackedItems() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(trackedItems);
        editor.putString("trackedItems", json);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the nav bar menu
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }

    // Logic for nav bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.btnHome) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (itemId == R.id.btnMenu) {
            // Already on Menu, do nothing or refresh
            return true;
        } else if (itemId == R.id.btnCalCalc) {
            startActivity(new Intent(this, CalorieActivity.class));
            return true;
        } else if (itemId == R.id.btnAbout) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (itemId == R.id.btnFeedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // RecyclerView Adapter for displaying menu items
    private class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
        private final List<FoodItem> menuItems;

        public MenuItemAdapter(List<FoodItem> menuItems) {
            this.menuItems = menuItems;
        }

        @NonNull
        @Override
        public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create new view holder for menu item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_menu, parent, false);
            return new MenuItemViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
            // Bind data to view holder
            FoodItem item = menuItems.get(position);
            holder.nameText.setText(item.getName());
            holder.ingredientsText.setText(item.getIngredients());
            holder.caloriesText.setText(item.getCalories() + " kcal");

            // Set click listener for "Add" button
            holder.addButton.setOnClickListener(v -> showQuantityDialog(item));
        }

        @Override
        public int getItemCount() {
            return menuItems.size();
        }

        // ViewHolder class that holds references to all views in a menu item card
        class MenuItemViewHolder extends RecyclerView.ViewHolder {
            TextView nameText, ingredientsText, caloriesText;
            Button addButton;

            public MenuItemViewHolder(@NonNull View itemView) {
                super(itemView);
                // Initialize all view references
                nameText = itemView.findViewById(R.id.item_name);
                ingredientsText = itemView.findViewById(R.id.item_ingredients);
                caloriesText = itemView.findViewById(R.id.item_calories);
                addButton = itemView.findViewById(R.id.add_button);
            }
        }
    }

    // Shows a dialog to select quantity when adding a food item to Cal Counter
    private void showQuantityDialog(FoodItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add " + item.getName() + " to Tracker");

        // Inflate custom dialog layout with quantity input
        View customLayout = getLayoutInflater().inflate(R.layout.dialog_quantity, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            // Handle adding the item with specified quantity
            EditText quantityInput = customLayout.findViewById(R.id.quantity_input);
            String quantityStr = quantityInput.getText().toString();
            if (!quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0) {
                    trackedItems.add(new TrackedFoodItem(item, quantity));
                    saveTrackedItems();
                    Toast.makeText(MenuActivity.this,
                            "Added " + quantity + " " + item.getName() + " to tracker",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}