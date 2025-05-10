
package com.project.mayin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ScoreActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton homeButtonPage = findViewById(R.id.homeButtonDon);

        //Veritabanından verileri çekme işlemi
        AppDatabase db = AppDatabase.getDatabase(this);
        ScoreDao scoreDao = db.scoreDao();
        List<Score> scores = scoreDao.getAll();
        Collections.reverse(scores);

        //Verileri RecyclerView'a bağlama işlemi
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        Score_RecycleViewAdapter adapter = new Score_RecycleViewAdapter(this, scores);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });
    }
    private void backToHomePage() {
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
