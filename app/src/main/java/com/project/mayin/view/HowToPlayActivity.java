package com.project.mayin.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.mayin.R;

public class HowToPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_how_to_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton homeButtonPage = findViewById(R.id.homeButtonDon);
        homeButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });
    }
    private void backToHomePage() {
        Intent intent = new Intent(HowToPlayActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}