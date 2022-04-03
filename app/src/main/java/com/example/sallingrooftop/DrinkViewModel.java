package com.example.sallingrooftop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {
    private LiveData<List<Drink>> allDrinks;
    private DrinkRepository drinkRepository;

    public DrinkViewModel(@NonNull Application application) {
        super(application);

        drinkRepository = new DrinkRepository(application);
        allDrinks = drinkRepository.getAllDrinks();
    }

    public LiveData<List<Drink>> getAllDrinks(){
        return allDrinks;
    }

    public void insertDrink(Drink drink){
        drinkRepository.insertDrink(drink);
    }
}
