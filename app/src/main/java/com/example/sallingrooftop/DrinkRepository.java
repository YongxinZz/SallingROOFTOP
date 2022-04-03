package com.example.sallingrooftop;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DrinkRepository {
    private DrinkDAO drinkDao;
    private LiveData<List<Drink>> allDrinks;

    public DrinkRepository(Application application){
        DrinkRoomDatabase db = DrinkRoomDatabase.getDatabase(application);
        drinkDao = db.drinkDao();
    }

    public LiveData<List<Drink>> getAllDrinks(){
        allDrinks = drinkDao.getAllDrinks();
        return allDrinks;
    }

    public void insertDrink(Drink drink){
        DrinkRoomDatabase.databaseWriteExecutor.execute(()->{
            drinkDao.insertDrink(drink);
        });
    }
}
