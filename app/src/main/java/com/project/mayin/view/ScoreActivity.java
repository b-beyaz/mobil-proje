
package com.project.mayin.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mayin.R;
import com.project.mayin.adapter.Score_RecycleViewAdapter;
import com.project.mayin.db.AppDatabase;
import com.project.mayin.db.ScoreDao;
import com.project.mayin.model.Score;

import java.util.Collections;
import java.util.List;

public class ScoreActivity extends AppCompatActivity{

    private SearchView searchView;
    private Score_RecycleViewAdapter adapter;
    private String queryText;
    private List<Score> scores;
    private ScoreDao scoreDao;
    private AppDatabase db;
    private RecyclerView recyclerView;

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
        searchView = findViewById(R.id.srcFilter); // layout'unuza SearchView eklemelisiniz
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryText = newText;
                filterScores();
                return false;
            }
        });
        ImageButton homeButtonPage = findViewById(R.id.homeButtonDon);

        //Veritabanından verileri çekme işlemi
        db = AppDatabase.getDatabase(this);
        scoreDao = db.scoreDao();
        scores = scoreDao.getAll();
        Collections.reverse(scores);

        //Verileri RecyclerView'a bağlama işlemi
        recyclerView = findViewById(R.id.mRecyclerView);
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
    private void filterScores() {
        if (queryText.isEmpty()) {
            Score_RecycleViewAdapter adapter = new Score_RecycleViewAdapter(this, scores);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            List<Score> filteredScores = scoreDao.findByName(queryText);
            Score_RecycleViewAdapter adapter = new Score_RecycleViewAdapter(this, filteredScores);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }


    }

    private void backToHomePage() {
        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
