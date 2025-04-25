package com.example.healthyfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalorieActivity extends AppCompatActivity {

    private List<TrackedFoodItem> trackedItems = new ArrayList<>();
    private TextView totalCaloriesText;
    private RecyclerView trackedItemsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        // Setup toolbar and hide default title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize UI components
        totalCaloriesText = findViewById(R.id.total_calories);
        trackedItemsRecycler = findViewById(R.id.tracked_items_recycler);
        Button clearButton = findViewById(R.id.clear_button);

        // Load tracked items and setup RecyclerView
        loadTrackedItems();
        trackedItemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        trackedItemsRecycler.setAdapter(new TrackedItemsAdapter());

        // Set click listener for clear button
        clearButton.setOnClickListener(v -> {
            trackedItems.clear();
            saveTrackedItems();
            updateUI();
        });

        // Update UI with loaded data
        updateUI();
    }

    // Updates the UI to reflect current tracked items and total calories
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void updateUI() {
        // Calculate total calories
        int totalCalories = 0;
        for (TrackedFoodItem item : trackedItems) {
            totalCalories += item.getTotalCalories();
        }
        totalCaloriesText.setText(totalCalories + " kcal");
        // Refresh RecyclerView data
        Objects.requireNonNull(trackedItemsRecycler.getAdapter()).notifyDataSetChanged();
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
        // Inflate the navigation bar menu
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation bar item clicks
        int itemId = item.getItemId();

        if (itemId == R.id.btnHome) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (itemId == R.id.btnMenu) {
            startActivity(new Intent(this, MenuActivity.class));
            return true;
        } else if (itemId == R.id.btnCalCalc) {
            // Already on Calorie Calculator, do nothing
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

    // RecyclerView Adapter for displaying tracked food items
    private class TrackedItemsAdapter extends RecyclerView.Adapter<TrackedItemsAdapter.TrackedItemViewHolder> {

        @NonNull
        @Override
        public TrackedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create new view holder for tracked item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.added_items, parent, false);
            return new TrackedItemViewHolder(view);
        }

        // Binds data to the view holder for a specific position
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull TrackedItemViewHolder holder, int position) {
            TrackedFoodItem item = trackedItems.get(position);
            holder.nameText.setText(item.getFoodItem().getName());
            holder.quantityText.setText(String.valueOf(item.getQuantity()));
            holder.caloriesText.setText(item.getFoodItem().getCalories() + " kcal each");
            holder.totalText.setText("Total: " + item.getTotalCalories() + " kcal");
        }

        @Override
        public int getItemCount() {
            return trackedItems.size();
        }

        // ViewHolder class that holds references to all views in a tracked item card
        class TrackedItemViewHolder extends RecyclerView.ViewHolder {
            TextView nameText, quantityText, caloriesText, totalText;

            public TrackedItemViewHolder(@NonNull View itemView) {
                super(itemView);
                // Initialize all view references
                nameText = itemView.findViewById(R.id.item_name);
                quantityText = itemView.findViewById(R.id.item_quantity);
                caloriesText = itemView.findViewById(R.id.item_calories);
                totalText = itemView.findViewById(R.id.item_total);
            }
        }
    }
}