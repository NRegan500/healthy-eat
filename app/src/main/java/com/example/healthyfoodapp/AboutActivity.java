package com.example.healthyfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        // Set up the toolbar with navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (itemId == R.id.btnMenu) {
            startActivity(new Intent(this, MenuActivity.class));
            return true;
        } else if (itemId == R.id.btnCalCalc) {
            startActivity(new Intent(this, CalorieActivity.class));
            return true;
        } else if (itemId == R.id.btnAbout) {
            // Already on home, do nothing or refresh
            return true;
        } else if (itemId == R.id.btnFeedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}