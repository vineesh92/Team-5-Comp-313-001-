package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MenuImage;
import com.squareup.picasso.Picasso;

import java.util.List;



public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.MyViewHolder> {

    private final List<MenuImage> mFacilities;

    public RestaurantMenuAdapter(List<MenuImage> facilities) {
        mFacilities = facilities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_single_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantMenuAdapter.MyViewHolder holder, final int position) {

        if (mFacilities.get(position).getMenuImage() != null &&
                !mFacilities.get(position).getMenuImage().equals("")) {
            Picasso.get()
                    .load(mFacilities.get(position).getMenuImage())
                    .placeholder(R.drawable.img_restaurant_no_image)
                    .into(holder.menuImage);
        }
    }

    @Override
    public int getItemCount() {
        return mFacilities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView menuImage;

        MyViewHolder(View view) {
            super(view);

            menuImage = view.findViewById(R.id.menuImage);

        }
    }
}