package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    private TextView easyHighScoreTextView;
    private TextView mediumHighScoreTextView;
    private TextView hardHighScoreTextView;
    private ImageButton settingsButtonPage;
    private ImageButton howToPlayPage;


    private ScoreDao scoreDao; // ScoreDao referansı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
*/
        // View referansları
        playerNameEditText = findViewById(R.id.playerNameEditText);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        //howToPlayPage =findViewById(R.id.howToPlay);
        //settingsButtonPage =findViewById(R.id.settingsButtonPage);
        easyHighScoreTextView = findViewById(R.id.easyHighScoreTextView);
        mediumHighScoreTextView = findViewById(R.id.mediumHighScoreTextView);
        hardHighScoreTextView = findViewById(R.id.hardHighScoreTextView);

        // ScoreDao'yu başlatıyoruz (veritabanı işlemleri için)
        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();

        // Her zorluk seviyesi için en yüksek skoru alıp gösteriyoruz
        showHighestScores();

        // Kolay butonuna tıklandığında
        easyButton.setOnClickListener(v -> startGame("Kolay"));

        // Orta butonuna tıklandığında
        mediumButton.setOnClickListener(v -> startGame("Orta"));

        // Zor butonuna tıklandığında
        hardButton.setOnClickListener(v -> startGame("Zor"));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Switch yerine if-else kullanalım
                if (item.getItemId() == R.id.item_x1) { // Ayarlar
                    // Ayarlar sayfasına geçiş
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.item_x2) { // Score Tablosu
                    // Score Tablosu sayfasına geçiş
                    Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.item_x3) { // HowToPlay Ekranı
                    // HowToPlay sayfasına geçiş
                    Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });

/*
        settingsButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsPage("settings");
            }
        });
        howToPlayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHowToPlayPage("howToPlay");
            }
        });*/
    }

    // Oyun başlatma fonksiyonu
    private void startGame(String difficulty) {
        String playerName = playerNameEditText.getText().toString().trim();
        if (playerName.isEmpty()) {
            // Kullanıcı adı boşsa, uyarı göster
            Toast.makeText(this, "Lütfen bir kullanıcı adı girin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // GameActivity'e oyuncu ismini ve zorluk seviyesini gönderiyoruz
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    // Her zorluk seviyesinin en yüksek skorlarını veritabanından alıp gösteren fonksiyon
    private void showHighestScores() {
        new Thread(() -> {
            Score easyScore = scoreDao.getHighestScoreForEasy();
            Score mediumScore = scoreDao.getHighestScoreForMedium();
            Score hardScore = scoreDao.getHighestScoreForHard();

            runOnUiThread(() -> {
                // Kolay seviyesindeki en yüksek skoru göster
                if (easyScore != null) {
                    easyHighScoreTextView.setText("Kolay: " + easyScore.score + " - " + easyScore.playerName);
                } else {
                    easyHighScoreTextView.setText("Kolay: 0 - Hiçbir oyuncu yok");
                }

                // Orta seviyesindeki en yüksek skoru göster
                if (mediumScore != null) {
                    mediumHighScoreTextView.setText("Orta: " + mediumScore.score + " - " + mediumScore.playerName);
                } else {
                    mediumHighScoreTextView.setText("Orta: 0 - Hiçbir oyuncu yok");
                }

                // Zor seviyesindeki en yüksek skoru göster
                if (hardScore != null) {
                    hardHighScoreTextView.setText("Zor: " + hardScore.score + " - " + hardScore.playerName);
                } else {
                    hardHighScoreTextView.setText("Zor: 0 - Hiçbir oyuncu yok");
                }
            });
        }).start();
    }
    private void showScorePage(String howTo) {
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class );
        intent.putExtra("score", howTo);

        startActivity(intent);
    }
    private void showSettingsPage(String settings) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("settings", settings);
        startActivity(intent);
    }

    private void showHowToPlayPage(String settings) {
        Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
        intent.putExtra("howToPlay", settings);
        startActivity(intent);
    }


}
