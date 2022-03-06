package com.example.sallingrooftop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    private List<Drink> sallingDrinks;

    public final TextView name;
    public final TextView description;
    public final TextView price;
    public final ImageView picture;

    public DrinkAdapter(List<Drink> sallingDrinks) {
        this.sallingDrinks = sallingDrinks;
    }

    @Override
    public DrinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drink drink = sallingDrinks.get(position);

        holder.name.setText(drink.getName());
        holder.description.setText(drink.getDescription());
        holder.price.setText(drink.getPrice());
        holder.picture.
    }

    @Override
    public int getItemCount() {
        return sallingDrinks.size();
    }



}
