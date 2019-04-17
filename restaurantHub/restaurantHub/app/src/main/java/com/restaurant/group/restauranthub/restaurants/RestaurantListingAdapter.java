package com.restaurant.manasa.restauranthub.ui.restaurants;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantListingAdapter extends RecyclerView.Adapter<RestaurantListingAdapter.ViewHolder> {


    private final Activity mContext;
    private final List<Restaurant> mRestaurantModels;

    public RestaurantListingAdapter(Activity context, List<Restaurant> restaurantModels) {
        mContext = context;
        mRestaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public RestaurantListingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant_listing, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListingAdapter.ViewHolder holder, int position) {


        if (mRestaurantModels != null && mRestaurantModels.get(position) != null) {
            if (mRestaurantModels.get(position).getPhotosUrl() != null &&
                    !mRestaurantModels.get(position).getPhotosUrl().equals("")) {
                Picasso.get().load(mRestaurantModels.get(position).getPhotosUrl())
                        .placeholder(R.drawable.ic_placeholder).error(R.drawable.img_restaurant_no_image)
                        .into(holder.imgRestaurantItemView);
            }

            /*if (mRestaurantModels.get(position).getAverageCostForTwo() != null &&
                    !mRestaurantModels.get(position).getAverageCostForTwo().equals("")) {
                holder.txtAverageCostView.setText(String.valueOf("₹ ".
                        concat(mRestaurantModels.get(position).getAverageCostForTwo())).concat(" For two"));
            }*/
            if (mRestaurantModels.get(position).getLocality() != null &&
                    !mRestaurantModels.get(position).getLocality().equals("")) {
                holder.txtLocalityView.setText(mRestaurantModels.get(position).getLocality());
            }
            if (mRestaurantModels.get(position).getCuisines() != null &&
                    !mRestaurantModels.get(position).getCuisines().equals("")) {
                holder.txtCuisinesView.setText(mRestaurantModels.get(position).getCuisines());
            }

            if (mRestaurantModels.get(position).getName() != null &&
                    !mRestaurantModels.get(position).getName().equals("")) {
                holder.txtRestaurantNameView.setText(mRestaurantModels.get(position).getName());
            }
            /*if (mRestaurantModels.get(position).getRating() != null &&
                    !mRestaurantModels.get(position).getRating().equals("")) {
                holder.ratingBarRatingsView.setRating(
                        Float.parseFloat(mRestaurantModels.get(position).getRating()));
                holder.ratingBarRatingsView.setIndicator(true);
                holder.txtNumberOfReviewView.setText(mRestaurantModels.get(position).getRating());
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgRestaurantItemView;
        private final TextView txtLocalityView;
        private final TextView txtCuisinesView;
        private final TextView txtRestaurantNameView;
        private final RatingBar ratingBarRatingsView;


        ViewHolder(View view) {
            super(view);

            imgRestaurantItemView = view.findViewById(R.id.imgRestaurantItemView);
//            txtAverageCostView = view.findViewById(R.id.txtAverageCostView);
            txtLocalityView = view.findViewById(R.id.txtLocalityView);
            txtCuisinesView = view.findViewById(R.id.txtCuisinesView);
            txtRestaurantNameView = view.findViewById(R.id.txtRestaurantNameView);
            ratingBarRatingsView = view.findViewById(R.id.ratingBarRatingsView);
//            txtNumberOfReviewView = view.findViewById(R.id.txtNumberOfReviewView);
        }
    }
}