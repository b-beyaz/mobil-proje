package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.project.mayin.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView scoreTextView;
    private TextView highestScoreTextView;
    private GridLayout gameBoard;
    private int score = 0;
    private boolean gameOver = false;
    private Button[][] buttons;
    private int[][] bombMap;
    private String difficulty;
    private String playerName;
    private ScoreDao scoreDao;
    ImageButton homeButtonPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();

        playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        /*playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);*/

        playerNameTextView.setText(getString(R.string.player_name_format, playerName));
        scoreTextView.setText(getString(R.string.score_format, score));


        setBoardDimensions();
        createGameBoard();
        placeMines();

        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        showHighestScore();
        homeButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });
    }

    private void backToHomePage() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showHighestScore() {
        new Thread(() -> {
            final Score highestScore;

            if (difficulty.equals("Kolay")) {
                highestScore = scoreDao.getHighestScoreForEasy();
            } else if (difficulty.equals("Orta")) {
                highestScore = scoreDao.getHighestScoreForMedium();
            } else if (difficulty.equals("Zor")) {
                highestScore = scoreDao.getHighestScoreForHard();
            } else {
                highestScore = null;
            }

            runOnUiThread(() -> {
                if (highestScore != null) {
                    String formattedText = getString(R.string.highest_score_format, highestScore.score, highestScore.playerName);
                    highestScoreTextView.setText(formattedText);
                } else {
                    highestScoreTextView.setText(getString(R.string.no_high_score));

                }
            });
        }).start();
    }

    private void setBoardDimensions() {
        int gridSize;
        switch (difficulty) {
            case "Kolay":
                gridSize = 6;
                break;
            case "Orta":
                gridSize = 8;
                break;
            case "Zor":
                gridSize = 10;
                break;
            default:
                gridSize = 8;
                break;
        }
        bombMap = new int[gridSize][gridSize];
        buttons = new Button[gridSize][gridSize];
    }

    private void createGameBoard() {
        int gridSize = buttons.length;
        gameBoard.setRowCount(gridSize);
        gameBoard.setColumnCount(gridSize);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = screenWidth / gridSize;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Button button = new Button(this);
                button.setText("");

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = buttonSize;
                params.height = buttonSize;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
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

        switch (difficulty) {
            case "Kolay":
                numberOfMines = 6;
                break;
            case "Orta":
                numberOfMines = 12;
                break;
            case "Zor":
                numberOfMines = 18;
                break;
            default:
                numberOfMines = 10;
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
        // Oyun bitmişse hiçbir işlem yapılmasın
        if (gameOver) return;

        // Eğer butona tıklanmışsa ve metin boş değilse, yani buton zaten açılmışsa tıklamayı engelle
        if (!buttons[row][col].getText().toString().isEmpty()) {
            return;
        }

        if (bombMap[row][col] == 1) {
            gameOver = true;
            buttons[row][col].setText("💣");  // Mayına basıldı
        } else {
            int adjacentBombs = countAdjacentBombs(row, col);
            buttons[row][col].setText(String.valueOf(adjacentBombs));
            score += 10;
            scoreTextView.setText(getString(R.string.score_format, score));
        }

        // Kazanma durumu kontrolü
        if (checkIfGameWon()) {
            gameOver = true; // Tüm güvenli kutular açıldıysa oyun kazanılır
        }

        // Eğer oyun bitmişse, kazandığınız veya kaybettiğiniz duruma göre dialog gösterilecek
        if (gameOver) {
            showRestartDialog(); // Kaybettiğiniz veya kazandığınızda doğru mesajı göster
        }

    }


    private boolean checkIfGameWon() {
        for (int row = 0; row < bombMap.length; row++) {
            for (int col = 0; col < bombMap[row].length; col++) {
                // Eğer bir hücrede bomba yoksa ve hala açılmamışsa, oyun kazanılmadı demektir
                if (bombMap[row][col] != 1 && buttons[row][col].getText().toString().isEmpty()) {
                    return false; // Hala açılmamış bir güvenli hücre varsa, oyun bitmedi
                }
            }
        }
        return true; // Tüm güvenli hücreler açıldığında oyun kazanılmıştır
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
    /*private void showRestartDialog() {
        // Eğer oyuncu kaybettiyse
        String title;
        String message;

        if (gameOver) {
            if (checkIfGameWon()) {
                title = getString(R.string.dialog_title_game_won); // Kazanma başlığı
                message = getString(R.string.dialog_message_game_won); // Kazanma mesajı
            } else {
                title = getString(R.string.dialog_title_game_over); // Kaybetme başlığı
                message = getString(R.string.dialog_message_game_over); // Kaybetme mesajı
            }
        } else {
            title = getString(R.string.dialog_title_game_won); // Kazanma başlığı
            message = getString(R.string.dialog_message_game_won); // Kazanma mesajı
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> restartGame())  // Oyunu yeniden başlat
                .setNegativeButton(getString(R.string.no), (dialog, which) -> backToHome())  // Ana sayfaya dön
                .setCancelable(false)
                .show();

        Toast.makeText(GameActivity.this, gameOver ? getString(R.string.toast_game_over) : getString(R.string.toast_game_won), Toast.LENGTH_SHORT).show();
    }*/
    private void showRestartDialog() {
        String title = "";  // Başlangıç değeri atanmalı
        String message = "";  // Başlangıç değeri atanmalı

        if (gameOver) {
            if (checkIfGameWon()) {
                title = getString(R.string.dialog_title_game_won); // Kazanma başlığı
                message = getString(R.string.dialog_message_game_won); // Kazanma mesajı
                Toast.makeText(GameActivity.this, getString(R.string.toast_game_won), Toast.LENGTH_SHORT).show();  // Kazanma mesajı

            } else {
                title = getString(R.string.dialog_title_game_over); // Kaybetme başlığı
                message = getString(R.string.dialog_message_game_over); // Kaybetme mesajı
                Toast.makeText(GameActivity.this, getString(R.string.toast_game_over), Toast.LENGTH_SHORT).show();  // Kaybetme mesajı

            }
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> restartGame())  // Oyunu yeniden başlat
                .setNegativeButton(getString(R.string.no), (dialog, which) -> backToHome())  // Ana sayfaya dön
                .setCancelable(false)
                .show();
    }
    private void backToHome() {
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void restartGame() {
        Score scoreEntry = new Score(playerName, difficulty, score);
        new Thread(() -> scoreDao.insertAll(scoreEntry)).start();

        score = 0;

        //scoreTextView.setText("Score: " + score);
        scoreTextView.setText(getString(R.string.score_format, score));


        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();
    }
    private void initComponents(){
        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);
        gameBoard = findViewById(R.id.gameBoard);
        homeButtonPage = findViewById(R.id.homeButtonDon);

    }
}



