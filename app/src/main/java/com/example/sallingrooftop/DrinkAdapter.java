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
    public DrinkAdapter(List<Drink> sallingDrinks) {
        this.sallingDrinks = sallingDrinks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView name;
        public final TextView description;
        public final TextView price;
        public final ImageView picture;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.drink_name);
            description = (TextView) itemView.findViewById(R.id.drink_description);
            price = (TextView) itemView.findViewById(R.id.drink_price);
            picture = (ImageView) itemView.findViewById(R.id.drink_picture);
        }
    }

    @Override
    public DrinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drink drink = sallingDrinks.get(position);

        holder.name.setText(drink.getName());
        holder.description.setText(drink.getDescription());
        holder.price.setText(drink.getPrice());
        holder.picture.setImageResource(drink.getPicture());
    }

    @Override
    public int getItemCount() {
        return sallingDrinks.size();
    }



    public void setDrinks(List<Drink> drinks){
        sallingDrinks = drinks;
        notifyDataSetChanged();
    }
}

