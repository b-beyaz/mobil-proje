package com.project.mayin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
        setContentView(R.layout.activity_main);

        initComponents();

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


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePlayerName(); // her yazı değişiminde kontrol et
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        playerNameEditText.addTextChangedListener(textWatcher);

    }
    private boolean validatePlayerName() {
        String name = playerNameEditText.getText().toString().trim();
        Drawable errorIcon = ContextCompat.getDrawable(this, R.drawable.uyari_icon); // hata ikonun varsa
        if (errorIcon != null) {
            int width = errorIcon.getIntrinsicWidth();
            int height = errorIcon.getIntrinsicHeight();
            errorIcon.setBounds(0, 0, width, height);
        }
        if (name.length() < 3) {
            playerNameEditText.setError(getString(R.string.error_name_too_short), errorIcon);
            return false;
        } else if (name.length() > 20) {
            playerNameEditText.setError(getString(R.string.error_name_too_long), errorIcon);
            return false;
        } else if (!name.matches("[a-zA-ZğüşıöçĞÜŞİÖÇ ]+")) {
            playerNameEditText.setError(getString(R.string.error_invalid_characters), errorIcon);
            return false;
        } else {
            playerNameEditText.setError(null);
            return true;
        }

    }

    // Oyun başlatma fonksiyonu
    private void startGame(String difficulty) {
        if (!validatePlayerName()) {
            return;
        }
        String playerName = playerNameEditText.getText().toString().trim();

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
                if (easyScore != null) {
                    easyHighScoreTextView.setText(getString(R.string.easy_score_format, easyScore.score, easyScore.playerName));
                } else {
                    easyHighScoreTextView.setText(getString(R.string.easy_no_score));
                }

                if (mediumScore != null) {
                    mediumHighScoreTextView.setText(getString(R.string.medium_score_format, mediumScore.score, mediumScore.playerName));
                } else {
                    mediumHighScoreTextView.setText(getString(R.string.medium_no_score));
                }

                if (hardScore != null) {
                    hardHighScoreTextView.setText(getString(R.string.hard_score_format, hardScore.score, hardScore.playerName));
                } else {
                    hardHighScoreTextView.setText(getString(R.string.hard_no_score));
                }
            });

        }).start();
    }
    private void initComponents(){
        // View referansları
        playerNameEditText = findViewById(R.id.playerNameEditText);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        easyHighScoreTextView = findViewById(R.id.easyHighScoreTextView);
        mediumHighScoreTextView = findViewById(R.id.mediumHighScoreTextView);
        hardHighScoreTextView = findViewById(R.id.hardHighScoreTextView);
        //howToPlayPage =findViewById(R.id.howToPlay);
        //settingsButtonPage =findViewById(R.id.settingsButtonPage);
    }
}
