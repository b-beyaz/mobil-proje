package com.project.mayin;

/*
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
*/





/*       4 NİSAN 2025 SABAHKİ HALİ EN SON GUNCEL HALİ BUYDU
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View referansları
        playerNameEditText = findViewById(R.id.playerNameEditText);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);

        // Kolay butonuna tıklandığında
        easyButton.setOnClickListener(v -> startGame("Kolay"));

        // Orta butonuna tıklandığında
        mediumButton.setOnClickListener(v -> startGame("Orta"));

        // Zor butonuna tıklandığında
        hardButton.setOnClickListener(v -> startGame("Zor"));

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);  // Bu satır, Toolbar'ı ActionBar olarak ayarlıyor
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menü kaynağını inflate ediyoruz
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menü öğesine tıklanıldığında yapılacak işlemler
        if (item.getItemId() == R.id.item_x1) {
            // X1'e tıklanınca yapılacak işlem
            Toast.makeText(this, "X1 Seçildi", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.item_x2) {
            // X2'ye tıklanınca yapılacak işlem
            Toast.makeText(this, "X2 Seçildi", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
}
 */

/*
  MACKBERRR EN GUNCEL HALİİİİİİİİİ
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private EditText playerNameEditText;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    private ScoreDao scoreDao;
    private TextView highestScoreTextView;

    private ImageButton settingsButtonPage;
    private ImageButton howToPlayPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View referansları
        playerNameEditText = findViewById(R.id.playerNameEditText);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);
        settingsButtonPage = findViewById(R.id.settingsButtonPage);
        howToPlayPage = findViewById(R.id.howToPlay);

        // Kolay butonuna tıklandığında
        easyButton.setOnClickListener(v -> startGame("Kolay"));

        // Orta butonuna tıklandığında
        mediumButton.setOnClickListener(v -> startGame("Orta"));

        // Zor butonuna tıklandığında
        hardButton.setOnClickListener(v -> startGame("Zor"));

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);  // Bu satır, Toolbar'ı ActionBar olarak ayarlıyor

        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        showHighestScore();
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
        });
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

    // En yüksek skoru veritabanından alıp gösteren fonksiyon
    private void showHighestScore() {
        // En yüksek skoru veritabanından alıyoruz
        new Thread(() -> {
            Score highestScore = scoreDao.getHighestScore(); // En yüksek skoru al
            runOnUiThread(() -> {
                if (highestScore != null) {
                    highestScoreTextView.setText("En Yüksek Skor: " + highestScore.score + " - " + highestScore.playerName);
                } else {
                    highestScoreTextView.setText("En Yüksek Skor: 0"); // Eğer hiç skor yoksa
                }
            });
        }).start();
    }
    private void showHowToPlayPage(String howTo) {
        Intent intent = new Intent(MainActivity.this,HowToPlayActivity.class );
        intent.putExtra("howToPlay", howTo);

        startActivity(intent);
    }


    private void showSettingsPage(String settings) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("settings", settings);
        startActivity(intent);
    }



}
*/

/*

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView easyHighScoreTextView;
    private TextView mediumHighScoreTextView;
    private TextView hardHighScoreTextView;

    private ScoreDao scoreDao; // ScoreDao referansı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View referansları
        easyHighScoreTextView = findViewById(R.id.easyHighScoreTextView);
        mediumHighScoreTextView = findViewById(R.id.mediumHighScoreTextView);
        hardHighScoreTextView = findViewById(R.id.hardHighScoreTextView);

        // ScoreDao'yu başlatıyoruz (veritabanı işlemleri için)
        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();

        // Her zorluk seviyesi için en yüksek skoru alıp gösteriyoruz
        showHighestScores();
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
}
*/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // View referansları
        playerNameEditText = findViewById(R.id.playerNameEditText);
        easyButton = findViewById(R.id.easyButton);
        mediumButton = findViewById(R.id.mediumButton);
        hardButton = findViewById(R.id.hardButton);
        howToPlayPage =findViewById(R.id.howToPlay);
        settingsButtonPage =findViewById(R.id.settingsButtonPage);



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
        });

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

    private void showHowToPlayPage(String howTo) {
        Intent intent = new Intent(MainActivity.this,HowToPlayActivity.class );
        intent.putExtra("howToPlay", howTo);

        startActivity(intent);
    }


    private void showSettingsPage(String settings) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        intent.putExtra("settings", settings);
        startActivity(intent);
    }
}
