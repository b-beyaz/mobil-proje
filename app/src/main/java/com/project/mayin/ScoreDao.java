package com.project.mayin;

/*

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
@Dao
public interface ScoreDao {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE player_name LIKE :searchText")
    List<Score> searchByAlbumOrArtist(String searchText);

    @Query("SELECT * FROM score WHERE difficulty = :difficulty")
    List<Score> getAllByDifficulty (String difficulty);



    @Insert
    void insertAll(Score... scores);

    @Query("SELECT * FROM score ORDER BY score DESC LIMIT 1")
    Score getHighestScore(); // En yüksek skoru alır
    // Diğer mevcut metodlar burada kalacak

    @Insert
    void insert(Score score);

    @Delete
    void delete(Score score);

}

*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.mayin.Score;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE player_name LIKE :searchText")
    List<Score> searchByAlbumOrArtist(String searchText);

    @Query("SELECT * FROM score WHERE difficulty = :difficulty")
    List<Score> getAllByDifficulty (String difficulty);
    // Kolay seviyesindeki en yüksek skoru al
    @Query("SELECT * FROM score WHERE difficulty = 'Kolay' ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForEasy();

    // Orta seviyesindeki en yüksek skoru al
    @Query("SELECT * FROM score WHERE difficulty = 'Orta' ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForMedium();

    // Zor seviyesindeki en yüksek skoru al
    @Query("SELECT * FROM score WHERE difficulty = 'Zor' ORDER BY score DESC LIMIT 1")
    Score getHighestScoreForHard();

    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);
}

