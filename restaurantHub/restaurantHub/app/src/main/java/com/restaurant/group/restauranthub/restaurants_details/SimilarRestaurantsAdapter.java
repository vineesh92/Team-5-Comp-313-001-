package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.SimilarRestaurants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SimilarRestaurantsAdapter extends RecyclerView.Adapter<SimilarRestaurantsAdapter.MyViewHolder> {


    private final Activity mContext;
    private final List<SimilarRestaurants> mSimilarRestaurantsList;


    SimilarRestaurantsAdapter(Activity context, List<SimilarRestaurants> mSimilarRestaurantsList) {
        this.mContext = context;
        this.mSimilarRestaurantsList = mSimilarRestaurantsList;
    }

    @NonNull
    @Override
    public SimilarRestaurantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_similar_restaurants, parent, false);

        return new SimilarRestaurantsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRestaurantsAdapter.MyViewHolder holder, final int position) {

        Picasso.get()
                .load(mSimilarRestaurantsList.get(position).getImage())
                .into(holder.mSimilarRestaurantImage);

        holder.mSimilarRestaurantName.setText(mSimilarRestaurantsList.get(position).getName());
        holder.mSimilarRestaurantAddress.setText(mSimilarRestaurantsList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mSimilarRestaurantsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView mSimilarRestaurantImage;
        final TextView mSimilarRestaurantName;
        final TextView mSimilarRestaurantAddress;

        MyViewHolder(View view) {
            super(view);

            mSimilarRestaurantImage = view.findViewById(R.id.imgRestaurantItemView);
            mSimilarRestaurantName = view.findViewById(R.id.txtRestaurantNameView);
            mSimilarRestaurantAddress = view.findViewById(R.id.txtLocalityView);
        }
    }
}