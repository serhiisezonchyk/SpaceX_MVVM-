package com.example.spacexships.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacexships.R;
import com.example.spacexships.model.Ship;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ShipsAdapter
        extends RecyclerView.Adapter<ShipsAdapter.ShipsViewHolder>
implements View.OnClickListener{
    private List<Ship> ships = Collections.emptyList();
    private ShipListener listener;
    public ShipsAdapter(ShipListener listener) {
        this.listener = listener;
    }

    public void setShipsList(List<Ship> ships){
        this.ships = ships;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ShipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_ship,parent,false);
        return new ShipsViewHolder(root,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipsViewHolder holder, int position) {
        Ship ship = ships.get(position);
        holder.ship_name.setText(ship.getShip_name());
        int year_built = ship.getYear_built();

        if(year_built != 0) holder.year_built.setText("Year of built: "+String.valueOf(year_built));
        else holder.year_built.setText("Year of built not found ");

        if(ship.getImage() != null)
            Picasso.get().load(ship.getImage()).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        holder.itemView.setTag(ship);
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

    @Override
    public void onClick(View view) {
        Ship ship = (Ship)view.getTag();
        listener.onShipChoosen(ship);
    }

    static class ShipsViewHolder extends  RecyclerView.ViewHolder{
        private TextView ship_name;
        private TextView year_built;
        private ImageView image;
        public ShipsViewHolder(@NonNull View itemView,View.OnClickListener listener) {
            super(itemView);
            ship_name = itemView.findViewById(R.id.ship_name);
            year_built = itemView.findViewById(R.id.year_built);
            image = itemView.findViewById(R.id.shipImageView);
            itemView.setOnClickListener(listener);
        }
    }
    public interface  ShipListener{
        void onShipChoosen(Ship ship);
    }
}
