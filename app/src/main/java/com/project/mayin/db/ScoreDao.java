package com.project.mayin.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.mayin.model.Score;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score")
    List<Score> getAll();

    // Kolay seviyesindeki en yüksek skoru alıyorz.
    @Query("SELECT * FROM score WHERE difficulty = 1 ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForEasy();

    // Orta seviyesindeki en yüksek skoru alıyoruz
    @Query("SELECT * FROM score WHERE difficulty = 2 ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForMedium();

    // Zor seviyesindeki en yüksek skoru alcaz
    @Query("SELECT * FROM score WHERE difficulty = 3 ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForHard();

    @Insert
    void insertAll(Score... scores);
    @Delete
    void delete(Score score);
}

