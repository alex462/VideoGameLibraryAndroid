package com.example.alexandrareinhart.videogamelibrary;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity //this is the object we are going to be persisting - this is the object for our table. We have to add a key.
public class VideoGame {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gameTitle;
    private String gameGenre;
    private boolean isCheckedOut;
    private Date date;

    public VideoGame(String gameTitle, String gameGenre, Date date) {
        this.gameTitle = gameTitle;
        this.gameGenre = gameGenre;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
