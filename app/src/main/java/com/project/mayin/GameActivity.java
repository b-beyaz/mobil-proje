package com.project.mayin;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView difficultyText = findViewById(R.id.difficultyText);
        String difficulty = getIntent().getStringExtra("difficulty");
        difficultyText.setText("Seçilen Zorluk: " + difficulty);

        // Burada seçilen zorluk seviyesine göre oyunu başlatabilirsiniz
    }
}