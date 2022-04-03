package com.example.sallingrooftop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ShowMenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter drinkAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Drink> drinks;
    private DrinkViewModel drinkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        getSupportActionBar().hide();

        //change title bar background color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        this.drinks = new ArrayList<Drink>();
        //createDrinksList();

        this.recyclerView = (RecyclerView) findViewById(R.id.drinkList);
        this.recyclerView.setHasFixedSize(true);

        //adding things to the recycleview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(itemDecoration);

        //use linear layout manager
        this.layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.drinkAdapter = new DrinkAdapter(drinks);

        drinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
        drinkViewModel.getAllDrinks().observe(this, new Observer<List<Drink>>() {
            @Override
            public void onChanged(List<Drink> drinks) {
                ((DrinkAdapter) drinkAdapter).setDrinks(drinks);
            }
        });

        this.recyclerView.setAdapter(drinkAdapter);
    }


}