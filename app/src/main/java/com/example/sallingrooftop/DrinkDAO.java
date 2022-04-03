package com.example.sallingrooftop;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrinkDAO {
    @Insert
    public void insertDrink(Drink drink);

    @Query("DELETE FROM Drinks")
    public void deleteAll();

    @Query("SELECT * from Drinks ORDER BY name")
    public LiveData<List<Drink>> getAllDrinks();
}
