package com.example.healthyfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar with navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Set up today's specials
        setupSpecials();

        // Set up menu categories
        setupCategories();

        // Set up buttons
        Button learnMoreButton = findViewById(R.id.learn_more_button);
        learnMoreButton.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));

        Button viewMenuButton = findViewById(R.id.view_menu_button);
        viewMenuButton.setOnClickListener(v -> startActivity(new Intent(this, MenuActivity.class)));
    }

    @SuppressLint("SetTextI18n")
    private void setupSpecials() {
        LinearLayout container = findViewById(R.id.specials_container);

        // Sample special items
        String[] specialNames = {"Superfood Bowl", "Grilled Salmon", "Vegan Cheesecake"};
        String[] specialIngredients = {"Quinoa, Kale, Avocado", "Salmon, Asparagus, Lemon", "Cashews, Dates, Coconut"};
        int[] specialCalories = {450, 420, 310};

        for (int i = 0; i < specialNames.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.home_items, container, false);

            // Set item data
            ((TextView)itemView.findViewById(R.id.item_name)).setText(specialNames[i]);
            ((TextView)itemView.findViewById(R.id.item_ingredients)).setText(specialIngredients[i]);
            ((TextView)itemView.findViewById(R.id.item_calories)).setText(specialCalories[i] + " kcal");

            // Make the entire item clickable
            itemView.setOnClickListener(v -> startActivity(new Intent(this, MenuActivity.class)));

            container.addView(itemView);
        }
    }

    private void setupCategories() {
        LinearLayout container = findViewById(R.id.categories_container);

        // Sample categories
        String[] categories = {"Starters", "Mains", "Desserts", "Drinks"};
        String[] categoryDescriptions = {"Light bites to begin", "Hearty main courses", "Sweet treats", "Refreshing beverages"};

        for (int i = 0; i < categories.length; i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.home_items, container, false);

            // Set category data
            ((TextView)itemView.findViewById(R.id.item_name)).setText(categories[i]);
            ((TextView)itemView.findViewById(R.id.item_ingredients)).setText(categoryDescriptions[i]);
            itemView.findViewById(R.id.item_calories).setVisibility(View.GONE);

            // Make the entire item clickable
            itemView.setOnClickListener(v -> startActivity(new Intent(this, MenuActivity.class)));

            container.addView(itemView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.btnHome) {
            // Already on home, do nothing or refresh
            return true;
        } else if (itemId == R.id.btnMenu) {
            startActivity(new Intent(this, MenuActivity.class));
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
}