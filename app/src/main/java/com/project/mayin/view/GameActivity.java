package com.project.mayin.view;

import static android.os.SystemClock.sleep;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.mayin.R;
import com.project.mayin.db.AppDatabase;
import com.project.mayin.db.ScoreDao;
import com.project.mayin.model.Score;

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
    private int difficulty;
    private String playerName;
    private ScoreDao scoreDao;
    ImageButton homeButtonPage;
    SoundPool soundPool;
    int soundCorrect, soundExplosion;
    Boolean soundOn = true;
    ImageButton btnSound;
    private MediaPlayer mediaPlayer;
    private TextView txtAnimation;
    ImageView endImage;

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
        soundInnit();
        playerName = getIntent().getStringExtra("playerName");
        difficulty = getIntent().getIntExtra("difficulty",2);
        endImage.setVisibility(INVISIBLE);

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

            if (difficulty == 1) {
                highestScore = scoreDao.getHighestScoreForEasy();
            } else if (difficulty == 2) {
                highestScore = scoreDao.getHighestScoreForMedium();
            } else if (difficulty == 3) {
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
            case 1:
                gridSize = 6;
                break;
            case 2:
                gridSize = 8;
                break;
            case 3:
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
                params.rowSpec = GridLayout.spec(row); // butonun satır indexini belirler
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
            case 1:
                numberOfMines = 6;
                break;
            case 2:
                numberOfMines = 12;
                break;
            case 3:
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

        // Eğer butona tıklanmışsa ve metin boş değilse yani buton zaten açılmışsa tıklamayı engelicez
        if (!buttons[row][col].getText().toString().isEmpty()) {
            return;
        }

        if (bombMap[row][col] == 1) {
            buttons[row][col].setText("💣");
            if (soundOn) soundPool.play(soundExplosion, 2, 2, 0, 0, 1); //Eğer ses açıksa patlama sesini çalar.
            gameOver = true;

        } else {
            if (soundOn) soundPool.play(soundCorrect, 2, 2, 0, 0, 1); //Eğer ses açıksa doğru basma sesini çalar.
            txtAnimation.setText("+10");
            txtAnimation.setVisibility(VISIBLE);

            // 1 saniye sonra gizler
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                txtAnimation.setVisibility(INVISIBLE); //
            }, 1000); // 1000 ms = 1 saniye
            int adjacentBombs = countAdjacentBombs(row, col);
            buttons[row][col].setText(String.valueOf(adjacentBombs));
            score += 10;
            scoreTextView.setText(getString(R.string.score_format, score));
        }

        // Kazanma durumu kontrolü
        if (checkIfGameWon()) {
            gameOver = true;
        }

        if (gameOver) {
            showFinishEvent();
        }

    }


    private boolean checkIfGameWon() {
        for (int row = 0; row < bombMap.length; row++) {
            for (int col = 0; col < bombMap[row].length; col++) {
                // Eğer bir hücrede bomba yoksa ve hala açılmamışsa, oyun kazanılmadı demektir
                if (bombMap[row][col] != 1 && buttons[row][col].getText().toString().isEmpty()) {
                    return false; // Hala açılmamış bir güvenli hücre varsa oyun bitmedi
                }
            }
        }
        return true; // Tüm güvenli hücreler açıldığında oyun kazanılır
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

    private void showFinishEvent() {
        String title = "";
        String message = "";

        if (gameOver) {
            if (checkIfGameWon()) {
                endImage.setImageResource(R.drawable.happy);
                endImage.setVisibility(View.VISIBLE);

                Toast.makeText(GameActivity.this, getString(R.string.toast_game_won), Toast.LENGTH_SHORT).show();

                // 1 saniye sonra dialogu göster
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    showRestartDialog(
                            getString(R.string.dialog_title_game_won),
                            getString(R.string.dialog_title_game_won)
                    );
                }, 2000);

            } else {
                // Kaybetme durumunda önce patlama resmini göster
                endImage.setImageResource(R.drawable.sad);
                endImage.setVisibility(View.VISIBLE);

                Toast.makeText(GameActivity.this, getString(R.string.toast_game_over), Toast.LENGTH_SHORT).show();

                // 1 saniye sonra dialogu göster
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    showRestartDialog(
                            getString(R.string.dialog_title_game_over),
                            getString(R.string.dialog_message_game_over)
                    );
                }, 2000);
            }
        }

    }
    private void showRestartDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> restartGame())
                .setNegativeButton(getString(R.string.no), (dialog, which) -> backToHome())
                .setCancelable(false)
                .create();

        alertDialog.show();

        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        negativeButton.setTextColor(ContextCompat.getColor(this, R.color.red));
        positiveButton.setTextColor(ContextCompat.getColor(this, R.color.green));
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
        btnSound = findViewById(R.id.btnSound);
        txtAnimation = findViewById(R.id.txtAnimation);
        endImage = findViewById(R.id.endImage);

    }

    private void soundInnit(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        soundCorrect = soundPool.load(this, R.raw.correct, 1);
        soundExplosion = soundPool.load(this, R.raw.explosion, 1);

        // Müzik dosyasını mediaPlayer nesnesine yükler
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.start();
    }

    public void soundOnOff(View view) {
        if (soundOn) {
            soundPool.autoPause();
            mediaPlayer.pause();
            soundOn = false;
            btnSound.setImageResource(R.drawable.sound_off);
        } else{
            soundPool.autoResume();
            mediaPlayer.start();
            soundOn = true;
            btnSound.setImageResource(R.drawable.sound_on);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        soundPool.release();
    }

}



