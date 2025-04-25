package com.example.healthyfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);

        // Set up the toolbar with navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize views
        TextInputEditText appFeedbackInput = findViewById(R.id.app_feedback_input);
        TextInputEditText menuFeedbackInput = findViewById(R.id.menu_feedback_input);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        Button submitButton = findViewById(R.id.submit_feedback_button);
        TextView thankYouMessage = findViewById(R.id.thank_you_message);

        submitButton.setOnClickListener(v -> {
            String appFeedback = Objects.requireNonNull(appFeedbackInput.getText()).toString().trim();
            String menuFeedback = Objects.requireNonNull(menuFeedbackInput.getText()).toString().trim();

            if (appFeedback.isEmpty() && menuFeedback.isEmpty()) {
                Toast.makeText(this, "Please provide some feedback", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show a thank you message
            appFeedbackInput.setText("");
            menuFeedbackInput.setText("");
            ratingBar.setRating(0);
            thankYouMessage.setVisibility(View.VISIBLE);

            // Hide the thank you message after 3 seconds
            new android.os.Handler().postDelayed(
                    () -> thankYouMessage.setVisibility(View.GONE),
                    3000
            );

            Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
        });
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
            startActivity(new Intent(this, MainActivity.class));
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
            // Already on Feedback, do nothing or refresh
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}