package com.project.mayin;

/*
package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons = new Button[8][8];
    private int[][] bombMap = new int[8][8]; // 0: no bomb, 1: bomb
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        String playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Oyun tahtasını oluşturuyoruz (8x8 Grid)
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();
    }

    private void createGameBoard() {
        gameBoard.setRowCount(8);
        gameBoard.setColumnCount(8);

        // 8x8 grid için butonlar oluşturuluyor
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak
                final int finalRow = row; // final ekledik
                final int finalCol = col; // final ekledik
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    // Mayınları rastgele yerleştiriyoruz
    private void placeMines() {
        Random rand = new Random();
        int numberOfMines = 10;  // Mayın sayısını belirleyebilirsiniz

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(8);
            int col = rand.nextInt(8);

            // Eğer o hücrede zaten mayın varsa, tekrar seçme
            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1;
            }
        }
    }

    // Grid hücresine tıklama işlemi
    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        // Mayına tıklanıp tıklanmadığını kontrol et
        if (bombMap[row][col] == 1) {
            // Mayına tıklanmışsa, oyun biter
            gameOver = true;
            buttons[row][col].setText("💣");  // Bomba simgesi ekliyoruz
            Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();

            // Oyun bitince, kullanıcıya oyun tekrar başlatılmasını öneriyoruz
            showRestartDialog();
            return;
        }

        // Mayın yoksa, etrafındaki sayıyı göster
        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        // Skor artır
        score += 10;
        scoreTextView.setText("Score: " + score);
    }

    // Bir hücrenin etrafındaki mayın sayısını hesapla
    private int countAdjacentBombs(int row, int col) {
        int count = 0;

        // 8 yönlü kontrol yapalım (üst, alt, sol, sağ, çaprazlar)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                // Hücrenin sınırlar içinde olup olmadığını kontrol et
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Oyun bittiğinde, kullanıcıya oyun başlatma seçeneği sunalım
    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> finish())  // Oyunu kapat
                .show();
    }

    // Oyunu zorluk seviyesine göre başlat
    private void restartGame() {
        // Yeni bir oyun başlatmak için Activity'yi yeniden başlatabiliriz.
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("difficulty", difficulty); // Seçilen zorluk seviyesi
        startActivity(intent);
        finish();  // Mevcut GameActivity'yi kapat
    }
}
 */

/*
package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons = new Button[8][8];
    private int[][] bombMap = new int[8][8]; // 0: no bomb, 1: bomb
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        String playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Oyun tahtasını oluşturuyoruz (8x8 Grid)
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();
    }

    private void createGameBoard() {
        gameBoard.setRowCount(8);
        gameBoard.setColumnCount(8);

        // 8x8 grid için butonlar oluşturuluyor
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak
                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    // Mayınları rastgele yerleştiriyoruz
    private void placeMines() {
        Random rand = new Random();
        int numberOfMines = 10;  // Mayın sayısını belirleyebilirsiniz

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(8);
            int col = rand.nextInt(8);

            // Eğer o hücrede zaten mayın varsa, tekrar seçme
            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1;
            }
        }
    }

    // Grid hücresine tıklama işlemi
    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        // Mayına tıklanıp tıklanmadığını kontrol et
        if (bombMap[row][col] == 1) {
            // Mayına tıklanmışsa, oyun biter
            gameOver = true;
            buttons[row][col].setText("💣");  // Bomba simgesi ekliyoruz
            Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();

            // Oyun bitince, kullanıcıya oyun tekrar başlatılmasını öneriyoruz
            showRestartDialog();
            return;
        }

        // Mayın yoksa, etrafındaki sayıyı göster
        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        // Skor artır
        score += 10;
        scoreTextView.setText("Score: " + score);
    }

    // Bir hücrenin etrafındaki mayın sayısını hesapla
    private int countAdjacentBombs(int row, int col) {
        int count = 0;

        // 8 yönlü kontrol yapalım (üst, alt, sol, sağ, çaprazlar)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                // Hücrenin sınırlar içinde olup olmadığını kontrol et
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Oyun bittiğinde, kullanıcıya oyun başlatma seçeneği sunalım
    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> finish())  // Oyunu kapat
                .show();
    }

    // Oyunu zorluk seviyesine göre başlat
    private void restartGame() {
        // Yeni bir oyun başlatmak için Activity'yi yeniden başlatabiliriz.
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("difficulty", difficulty); // Seçilen zorluk seviyesi
        startActivity(intent);
        finish();  // Mevcut GameActivity'yi kapat
    }
}
*/

/*
package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons = new Button[8][8];
    private int[][] bombMap = new int[8][8]; // 0: no bomb, 1: bomb
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        String playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Oyun tahtasını oluşturuyoruz (8x8 Grid)
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();
    }

    private void createGameBoard() {
        gameBoard.setRowCount(8);
        gameBoard.setColumnCount(8);

        // 8x8 grid için butonlar oluşturuluyor
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak
                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    // Mayınları rastgele yerleştiriyoruz
    private void placeMines() {
        Random rand = new Random();
        int numberOfMines;

        // Zorluk seviyesine göre mayın sayısını ayarlıyoruz
        switch (difficulty) {
            case "Kolay":
                numberOfMines = 8; // Kolay seviyede daha az mayın
                break;
            case "Orta":
                numberOfMines = 12; // Orta seviyede daha fazla mayın
                break;
            case "Zor":
                numberOfMines = 17; // Zor seviyede en fazla mayın
                break;
            default:
                numberOfMines = 10; // Varsayılan olarak orta seviye
                break;
        }

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(8);
            int col = rand.nextInt(8);

            // Eğer o hücrede zaten mayın varsa, tekrar seçme
            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1; // Mayın yerleştir
            }
        }
    }

    // Grid hücresine tıklama işlemi
    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        // Mayına tıklanıp tıklanmadığını kontrol et
        if (bombMap[row][col] == 1) {
            // Mayına tıklanmışsa, oyun biter
            gameOver = true;
            buttons[row][col].setText("💣");  // Bomba simgesi ekliyoruz
            Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();

            // Oyun bitince, kullanıcıya oyun tekrar başlatılmasını öneriyoruz
            showRestartDialog();
            return;
        }

        // Mayın yoksa, etrafındaki sayıyı göster
        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        // Skor artır
        score += 10;
        scoreTextView.setText("Score: " + score);
    }

    // Bir hücrenin etrafındaki mayın sayısını hesapla
    private int countAdjacentBombs(int row, int col) {
        int count = 0;

        // 8 yönlü kontrol yapalım (üst, alt, sol, sağ, çaprazlar)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                // Hücrenin sınırlar içinde olup olmadığını kontrol et
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Oyun bittiğinde, kullanıcıya oyun başlatma seçeneği sunalım
    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> finish())  // Oyunu kapat
                .show();
    }

    // Oyunu zorluk seviyesine göre başlat
    private void restartGame() {
        // Yeni bir oyun başlatmak için Activity'yi yeniden başlatabiliriz.
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("difficulty", difficulty); // Seçilen zorluk seviyesi
        startActivity(intent);
        finish();  // Mevcut GameActivity'yi kapat
    }
}
*/


/*    4 NİSAN 2025 SABAHKİ HALİ EN GUCEL HALİİİİİİİİİİİİ
package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons = new Button[8][8];
    private int[][] bombMap = new int[8][8]; // 0: no bomb, 1: bomb
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        String playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Oyun tahtasını oluşturuyoruz (8x8 Grid)
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();
    }

    private void createGameBoard() {
        gameBoard.setRowCount(8);
        gameBoard.setColumnCount(8);

        // Grid'in her hücresinde kare butonlar oluşturuluyor
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak

                // GridLayout'taki butonların kare olması için boyutları ayarlıyoruz
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;  // Grid'de butonun genişliği
                params.height = 0;  // Grid'de butonun yüksekliği
                params.rowSpec = GridLayout.spec(row, 1f);  // Satır için esnek alan
                params.columnSpec = GridLayout.spec(col, 1f);  // Sütun için esnek alan
                button.setLayoutParams(params);  // Parametreyi butona atıyoruz

                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    // Mayınları rastgele yerleştiriyoruz
    private void placeMines() {
        Random rand = new Random();
        int numberOfMines;

        // Zorluk seviyesine göre mayın sayısını ayarlıyoruz
        switch (difficulty) {
            case "Kolay":
                numberOfMines = 8; // Kolay seviyede daha az mayın
                break;
            case "Orta":
                numberOfMines = 12; // Orta seviyede daha fazla mayın
                break;
            case "Zor":
                numberOfMines = 17; // Zor seviyede en fazla mayın
                break;
            default:
                numberOfMines = 10; // Varsayılan olarak orta seviye
                break;
        }

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(8);
            int col = rand.nextInt(8);

            // Eğer o hücrede zaten mayın varsa, tekrar seçme
            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1; // Mayın yerleştir
            }
        }
    }

    // Grid hücresine tıklama işlemi
    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        // Mayına tıklanıp tıklanmadığını kontrol et
        if (bombMap[row][col] == 1) {
            // Mayına tıklanmışsa, oyun biter
            gameOver = true;
            buttons[row][col].setText("💣");  // Bomba simgesi ekliyoruz
            Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();

            // Oyun bitince, kullanıcıya oyun tekrar başlatılmasını öneriyoruz
            showRestartDialog();
            return;
        }

        // Mayın yoksa, etrafındaki sayıyı göster
        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        // Skor artır
        score += 10;
        scoreTextView.setText("Score: " + score);
    }

    // Bir hücrenin etrafındaki mayın sayısını hesapla
    private int countAdjacentBombs(int row, int col) {
        int count = 0;

        // 8 yönlü kontrol yapalım (üst, alt, sol, sağ, çaprazlar)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                // Hücrenin sınırlar içinde olup olmadığını kontrol et
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Oyun bittiğinde, kullanıcıya oyun başlatma seçeneği sunalım
    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> finish())  // Oyunu kapat
                .show();
    }

    // Oyunu zorluk seviyesine göre başlat
    private void restartGame() {
        // Yeni bir oyun başlatmak için Activity'yi yeniden başlatabiliriz.
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("difficulty", difficulty); // Seçilen zorluk seviyesi
        startActivity(intent);
        finish();  // Mevcut GameActivity'yi kapat
    }
}
 */


/*

package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mayin.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons;
    private int[][] bombMap;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        String playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Zorluk seviyesine göre grid boyutunu ve mayın sayısını belirliyoruz
        setBoardDimensions();

        // Oyun tahtasını oluşturuyoruz
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();
    }

    private void setBoardDimensions() {
        int gridSize;
        switch (difficulty) {
            case "Kolay":
                gridSize = 6; // Kolay seviyede daha küçük grid
                break;
            case "Orta":
                gridSize = 8; // Orta seviyede standart grid
                break;
            case "Zor":
                gridSize = 10; // Zor seviyede büyük grid
                break;
            default:
                gridSize = 8; // Varsayılan olarak orta seviye
                break;
        }
        bombMap = new int[gridSize][gridSize]; // Mayın haritası
        buttons = new Button[gridSize][gridSize]; // Buton dizisi
    }

    private void createGameBoard() {
        int gridSize = buttons.length;
        gameBoard.setRowCount(gridSize);
        gameBoard.setColumnCount(gridSize);

        // Grid'in her hücresinde kare butonlar oluşturuluyor
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak

                // GridLayout'taki butonların kare olması için boyutları ayarlıyoruz
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                button.setLayoutParams(params);

                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int numberOfMines;

        // Zorluk seviyesine göre mayın sayısını ayarlıyoruz
        switch (difficulty) {
            case "Kolay":
                numberOfMines = 6; // Kolay seviyede daha az mayın
                break;
            case "Orta":
                numberOfMines = 12; // Orta seviyede daha fazla mayın
                break;
            case "Zor":
                numberOfMines = 18; // Zor seviyede en fazla mayın
                break;
            default:
                numberOfMines = 10; // Varsayılan olarak orta seviye
                break;
        }

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(buttons.length);
            int col = rand.nextInt(buttons[0].length);

            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1;
            }
        }
    }

    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        if (bombMap[row][col] == 1) {
            gameOver = true;
            buttons[row][col].setText("💣");
            Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();
            showRestartDialog();
            return;
        }

        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        score += 10;
        scoreTextView.setText("Score: " + score);
    }

    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        int gridSize = bombMap.length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> finish())
                .show();
    }

    private void restartGame() {
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();
    }
}



*/

/*
  MACKBERRR EN GUNCEL HALİİİİİİİİİ


package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mayin.R;

import java.util.Random;
public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons;
    private int[][] bombMap;
    private String difficulty;
    private String playerName;

    private ScoreDao scoreDao; // ScoreDao referansı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Zorluk seviyesine göre grid boyutunu ve mayın sayısını belirliyoruz
        setBoardDimensions();

        // Oyun tahtasını oluşturuyoruz
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();

        // ScoreDao'yu başlatıyoruz (veritabanı işlemleri için)
        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        showHighestScore();
    }

    private void showHighestScore() {
        // En yüksek skoru veritabanından alıyoruz
        new Thread(() -> {
            Score highestScore = scoreDao.getHighestScore(); // En yüksek skoru al
            runOnUiThread(() -> {
                if (highestScore != null) {
                    scoreTextView.setText("En Yüksek Skor: " + highestScore.score); // Skoru ekranda göster
                }
            });
        }).start();
    }


    private void setBoardDimensions() {
        int gridSize;
        switch (difficulty) {
            case "Kolay":
                gridSize = 6; // Kolay seviyede daha küçük grid
                break;
            case "Orta":
                gridSize = 8; // Orta seviyede standart grid
                break;
            case "Zor":
                gridSize = 10; // Zor seviyede büyük grid
                break;
            default:
                gridSize = 8; // Varsayılan olarak orta seviye
                break;
        }
        bombMap = new int[gridSize][gridSize]; // Mayın haritası
        buttons = new Button[gridSize][gridSize]; // Buton dizisi
    }

    private void createGameBoard() {
        int gridSize = buttons.length;
        gameBoard.setRowCount(gridSize);
        gameBoard.setColumnCount(gridSize);

        // Grid'in her hücresinde kare butonlar oluşturuluyor
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak

                // GridLayout'taki butonların kare olması için boyutları ayarlıyoruz
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                button.setLayoutParams(params);

                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int numberOfMines;

        // Zorluk seviyesine göre mayın sayısını ayarlıyoruz
        switch (difficulty) {
            case "Kolay":
                numberOfMines = 6; // Kolay seviyede daha az mayın
                break;
            case "Orta":
                numberOfMines = 12; // Orta seviyede daha fazla mayın
                break;
            case "Zor":
                numberOfMines = 18; // Zor seviyede en fazla mayın
                break;
            default:
                numberOfMines = 10; // Varsayılan olarak orta seviye
                break;
        }

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(buttons.length);
            int col = rand.nextInt(buttons[0].length);

            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1;
            }
        }
    }

    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        if (bombMap[row][col] == 1) {
            gameOver = true;
            buttons[row][col].setText("💣");
            //Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();
            showRestartDialog();
            return;
        }

        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        score += 100;
        scoreTextView.setText("Score: " + score);
    }

    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        int gridSize = bombMap.length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> backToHome())
                .setCancelable(false)  // Dışarıya tıklanmasını engelle
                .setOnCancelListener(dialog -> {
                    // Eğer kullanıcı dışarıya tıkladıysa, işlem yapılmaz
                    // Burada bir şey yapmamıza gerek yok çünkü dışarıya tıklanması engellenmiş olacak
                })
                .show();
        Toast.makeText(GameActivity.this,"Lütfen evet ya da hayıra tıklaynız",Toast.LENGTH_LONG).show();

    }

    private void backToHome(){
        // Veritabanına oyuncu puanını kaydediyoruz
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
    private void restartGame() {
        // Veritabanına oyuncu puanını kaydediyoruz
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();

        // Oyunun yeni başladığını göstermek için score sıfırlanıyor
        score = 0; // Yeni oyun için skoru sıfırlıyoruz
        scoreTextView.setText("Score: " + score);

        // Yeni oyunu başlatıyoruz
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);  // Oyuncu ismini tekrar gönderiyoruz
        intent.putExtra("difficulty", difficulty);  // Zorluk seviyesini tekrar gönderiyoruz
        startActivity(intent);
        finish();
    }

}
*/

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mayin.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private TextView highestScoreTextView;  // Yeni eklediğimiz TextView
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons;
    private int[][] bombMap;
    private String difficulty;
    private String playerName;

    private ScoreDao scoreDao; // ScoreDao referansı

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View'leri bağlama
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);  // Yeni TextView
        gameBoard = findViewById(R.id.gameBoard);

        // Intent'ten oyuncu ismini ve zorluk seviyesini alıyoruz
        playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        // Zorluk seviyesine göre grid boyutunu ve mayın sayısını belirliyoruz
        setBoardDimensions();

        // Oyun tahtasını oluşturuyoruz
        createGameBoard();
        // Mayınları yerleştiriyoruz
        placeMines();

        // ScoreDao'yu başlatıyoruz (veritabanı işlemleri için)
        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        showHighestScore();  // Her zorluk seviyesi için en yüksek skoru gösteriyoruz
    }

    private void showHighestScore() {
        // Seçilen zorluk seviyesine göre en yüksek skoru veritabanından alıyoruz
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Score highestScore; // final yapıyoruz

                // Zorluk seviyesine göre en yüksek skoru al
                if (difficulty.equals("Kolay")) {
                    highestScore = scoreDao.getHighestScoreForEasy();
                } else if (difficulty.equals("Orta")) {
                    highestScore = scoreDao.getHighestScoreForMedium();
                } else if (difficulty.equals("Zor")) {
                    highestScore = scoreDao.getHighestScoreForHard();
                } else {
                    highestScore = null;
                }

                // UI thread'inde en yüksek skoru gösteriyoruz
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 'highestScoreTextView' ve 'highestScore' final olduğu için burada kullanabiliriz
                        if (highestScore != null) {
                            highestScoreTextView.setText("En Yüksek Skor: " + highestScore.score + " - " + highestScore.playerName);
                        } else {
                            highestScoreTextView.setText("En Yüksek Skor: 0 - Hiçbir oyuncu yok");
                        }
                    }
                });
            }
        }).start();
    }

    private void setBoardDimensions() {
        int gridSize;
        switch (difficulty) {
            case "Kolay":
                gridSize = 6; // Kolay seviyede daha küçük grid
                break;
            case "Orta":
                gridSize = 8; // Orta seviyede standart grid
                break;
            case "Zor":
                gridSize = 10; // Zor seviyede büyük grid
                break;
            default:
                gridSize = 8; // Varsayılan olarak orta seviye
                break;
        }
        bombMap = new int[gridSize][gridSize]; // Mayın haritası
        buttons = new Button[gridSize][gridSize]; // Buton dizisi
    }

    private void createGameBoard() {
        int gridSize = buttons.length;
        gameBoard.setRowCount(gridSize);
        gameBoard.setColumnCount(gridSize);

        // Grid'in her hücresinde kare butonlar oluşturuluyor
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button button = new Button(this);
                button.setText("");  // Her buton başlangıçta boş olacak

                // GridLayout'taki butonların kare olması için boyutları ayarlıyoruz
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                button.setLayoutParams(params);

                final int finalRow = row;
                final int finalCol = col;
                button.setOnClickListener(v -> onGridCellClicked(finalRow, finalCol));
                buttons[row][col] = button;
                gameBoard.addView(button);
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int numberOfMines;

        // Zorluk seviyesine göre mayın sayısını ayarlıyoruz
        switch (difficulty) {
            case "Kolay":
                numberOfMines = 6; // Kolay seviyede daha az mayın
                break;
            case "Orta":
                numberOfMines = 12; // Orta seviyede daha fazla mayın
                break;
            case "Zor":
                numberOfMines = 18; // Zor seviyede en fazla mayın
                break;
            default:
                numberOfMines = 10; // Varsayılan olarak orta seviye
                break;
        }

        for (int i = 0; i < numberOfMines; i++) {
            int row = rand.nextInt(buttons.length);
            int col = rand.nextInt(buttons[0].length);

            if (bombMap[row][col] == 1) {
                i--;
            } else {
                bombMap[row][col] = 1;
            }
        }
    }

    private void onGridCellClicked(int row, int col) {
        if (gameOver) return;

        if (bombMap[row][col] == 1) {
            gameOver = true;
            buttons[row][col].setText("💣");
            //Toast.makeText(this, "Game Over! Mayına tıkladınız.", Toast.LENGTH_SHORT).show();
            showRestartDialog();
            return;
        }

        int adjacentBombs = countAdjacentBombs(row, col);
        buttons[row][col].setText(String.valueOf(adjacentBombs));

        score += 100;
        scoreTextView.setText("Score: " + score);
    }

    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        int gridSize = bombMap.length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize) {
                    if (bombMap[newRow][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Oyun Bitti!")
                .setMessage("Game Over! Oyun tekrar başlatılsın mı?")
                .setPositiveButton("Evet", (dialog, which) -> restartGame())
                .setNegativeButton("Hayır", (dialog, which) -> backToHome())
                .setCancelable(false)  // Dışarıya tıklanmasını engelle
                .show();
        Toast.makeText(GameActivity.this,"Lütfen evet ya da hayıra tıklaynız",Toast.LENGTH_LONG).show();

    }

    private void backToHome(){
        // Veritabanına oyuncu puanını kaydediyoruz
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
    private void restartGame() {
        // Veritabanına oyuncu puanını kaydediyoruz
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();

        // Oyunun yeni başladığını göstermek için score sıfırlanıyor
        score = 0; // Yeni oyun için skoru sıfırlıyoruz
        scoreTextView.setText("Score: " + score);

        // Yeni oyunu başlatıyoruz
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);  // Oyuncu ismini tekrar gönderiyoruz
        intent.putExtra("difficulty", difficulty);  // Zorluk seviyesini tekrar gönderiyoruz
        startActivity(intent);
        finish();
    }

}


