package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
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



        playerNameTextView = findViewById(R.id.playerNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        highestScoreTextView = findViewById(R.id.highestScoreTextView);
        gameBoard = findViewById(R.id.gameBoard);

        playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getStringExtra("difficulty");

        playerNameTextView.setText("Player: " + playerName);
        scoreTextView.setText("Score: " + score);

        setBoardDimensions();
        createGameBoard();
        placeMines();

        AppDatabase db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        showHighestScore();
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
                    highestScoreTextView.setText("En Yüksek Skor: " + highestScore.score + " - " + highestScore.playerName);
                } else {
                    highestScoreTextView.setText("En Yüksek Skor: 0 - Hiçbir oyuncu yok");
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
        if (gameOver) return;

        if (bombMap[row][col] == 1) {
            gameOver = true;
            buttons[row][col].setText("💣");
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
                .setNegativeButton("Hayır", (dialog, which) -> backToHome())
                .setCancelable(false)
                .show();

        Toast.makeText(GameActivity.this, "Lütfen evet ya da hayıra tıklayınız", Toast.LENGTH_LONG).show();
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
        scoreTextView.setText("Score: " + score);

        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();
    }
}



