package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    databaseTest();// Veritabanı testi

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
    }

    public void howToPlay(View view) {// Nasıl oynanır sayfasına geçiş
        Intent intent = new Intent(this, HowToPlayActivity.class);
        startActivity(intent);
    }
    private void databaseTest() {
        AppDatabase db = AppDatabase.getDatabase(this);// Veritabanı nesnesini oluşturur
        ScoreDao scoreDao = db.scoreDao();// ScoreDao nesnesini oluşturur

        Score score = new Score("Player2", "Kolay", 200);// Yeni bir skor nesnesi oluşturur
        scoreDao.insertAll(score);// Veritabanına skor nesnesini ekler

        TextView databaseTest = findViewById(R.id.databaseTest);

        List<Score> scores = scoreDao.getAll();// Tüm skorları alır
        databaseTest.setText(scores.toString());// Skorları TextView'e yazdırır
    }
}
