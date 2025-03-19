package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button easyButton = findViewById(R.id.easyButton);
    Button mediumButton = findViewById(R.id.mediumButton);
    Button hardButton = findViewById(R.id.hardButton);

    easyButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Kolay seviye için işlemler
            startGame("Kolay");
        }
    });
    mediumButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Orta seviye için işlemler
            startGame("Orta");
        }
    });

    hardButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Zor seviye için işlemler
            startGame("Zor");
        }
    });

}
    private void startGame(String difficulty) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }}
