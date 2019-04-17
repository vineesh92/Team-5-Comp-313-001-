package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restaurant.manasa.restauranthub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDetailsImageAdapter extends RecyclerView.Adapter<RestaurantDetailsImageAdapter.MyViewHolder> {

    private List<String> mUrlList = new ArrayList<>();
    private Context mContext;

    public RestaurantDetailsImageAdapter(List<String> mUrlList) {
        this.mUrlList = mUrlList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_images, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Picasso.get()
                .load(mUrlList.get(position))
                .into(holder.mRestaurantImageView);
    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mRestaurantImageView;

        MyViewHolder(View itemView) {
            super(itemView);

            mRestaurantImageView = itemView.findViewById(R.id.image_item_restaurant_images);
        }
    }
}
