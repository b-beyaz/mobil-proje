
package com.project.mayin.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "player_name")
    public String playerName;
    @ColumnInfo(name = "difficulty")
    public int difficulty;
    @ColumnInfo(name = "score")
    public int score;
    public Score(String playerName, int difficulty, int score) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.score = score;
    }

    @Override
    public String toString() { // Test amaçlı olmak üzere toString() metodu ekledim
        return uid + " " + playerName + " " + difficulty + " " + score + "\n";
    }



}