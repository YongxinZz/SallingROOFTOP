package com.example.sallingrooftop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Drink.class}, version = 1, exportSchema = false)
public abstract class DrinkRoomDatabase extends RoomDatabase {

    public abstract DrinkDAO drinkDao();

    private static final String DRINK_DB_NAME = "drink_db";
    private static volatile DrinkRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DrinkRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DrinkRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DrinkRoomDatabase.class, DRINK_DB_NAME).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DrinkDAO dao = INSTANCE.drinkDao();
//                dao.deleteAll();
//
//                dao.deleteAll();
                Drink coffee = new Drink("Coffee", "", "25,-", R.drawable.black_coffee);
                dao.insertDrink(coffee);
                Drink teaGlass = new Drink("Tea (cup)", "NuTe, refill included", "25,-", R.drawable.tea_in_cup);
                dao.insertDrink(teaGlass);
                Drink teaPot = new Drink("Tea (pot)", "NuTe, with three cups", "60,-", R.drawable.tea_in_pot);
                dao.insertDrink(teaPot);
                Drink espresso = new Drink("Espresso", "single/double", "20,-/30,-", R.drawable.espresso_coffee);
                dao.insertDrink(espresso);
                Drink latte = new Drink("Cafe Latte", "", "35,-", R.drawable.latte);
                dao.insertDrink(latte);
                Drink cappuccino = new Drink("Cappuccino", "", "35,-", R.drawable.cappuccino);
                dao.insertDrink(cappuccino);
                Drink cafeAuLait = new Drink("Cafe au lait", "", "35,-", R.drawable.cafe_au_lait);
                dao.insertDrink(cafeAuLait);
                Drink hotChoco = new Drink("Hot Chocolate", "", "35,-", R.drawable.hot_chocolate);
                dao.insertDrink(hotChoco);
                Drink icedCoffee = new Drink("Iced Coffee", "", "40,-", R.drawable.iced_coffee);
                dao.insertDrink(icedCoffee);
            });
        }
    };
}
