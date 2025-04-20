package com.project.mayin;
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

    @Delete
    void delete(Score score);

}
