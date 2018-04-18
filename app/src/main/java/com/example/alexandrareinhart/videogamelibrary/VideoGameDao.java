package com.example.alexandrareinhart.videogamelibrary;
//data access object - allows us to access our persisted objects
//our data accessed file

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VideoGameDao {

    //Allow us to get all video games
    @Query("SELECT * FROM videogame")
    List<VideoGame> getVideoGames();

    //Allow us to add a single game to list
    @Insert
    void addVideoGame(VideoGame videoGame);

    //Allow us to update the values of an existing game
    @Update
    void updateVideoGame(VideoGame videoGame);

    //Allow us to delete a game from the library
    @Delete
    void deleteVideoGame(VideoGame videoGame);
}
