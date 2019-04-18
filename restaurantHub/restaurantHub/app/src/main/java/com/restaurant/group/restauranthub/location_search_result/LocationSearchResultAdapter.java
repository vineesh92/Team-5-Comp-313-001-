package com.restaurant.manasa.restauranthub.ui.location_search_result;

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

public class LocationSearchResultAdapter extends RecyclerView.Adapter<LocationSearchResultAdapter.ViewHolder> {

    private final Activity mContext;
    private final List<Restaurant> mRestaurantSearchModels;

    public LocationSearchResultAdapter(Activity mContext, List<Restaurant> mRestaurantSearchModels) {
        this.mContext = mContext;
        this.mRestaurantSearchModels = mRestaurantSearchModels;
    }

    @NonNull
    @Override
    public LocationSearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant_listing, parent, false);

        return new LocationSearchResultAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationSearchResultAdapter.ViewHolder holder, int position) {
        if (mRestaurantSearchModels != null && mRestaurantSearchModels.get(position) != null) {
            if (mRestaurantSearchModels.get(position).getPhotosUrl() != null &&
                    !mRestaurantSearchModels.get(position).getPhotosUrl().equals("")) {
                Picasso.get().load(mRestaurantSearchModels.get(position).getPhotosUrl())
                        .placeholder(R.drawable.ic_placeholder).error(R.drawable.img_restaurant_no_image)
                        .into(holder.imgRestaurantItemView);
            }

            /*if (mRestaurantSearchModels.get(position).getAverageCostForTwo() != null &&
                    !mRestaurantSearchModels.get(position).getAverageCostForTwo().equals("")) {
                holder.txtAverageCostView.setText(String.valueOf("₹ ".
                        concat(mRestaurantSearchModels.get(position).getAverageCostForTwo())).concat(" For two"));
            }*/
            if (mRestaurantSearchModels.get(position).getLocality() != null &&
                    !mRestaurantSearchModels.get(position).getLocality().equals("")) {
                holder.txtLocalityView.setText(mRestaurantSearchModels.get(position).getLocality());
            }
            if (mRestaurantSearchModels.get(position).getCuisines() != null &&
                    !mRestaurantSearchModels.get(position).getCuisines().equals("")) {
                holder.txtCuisinesView.setText(mRestaurantSearchModels.get(position).getCuisines());
            }

            if (mRestaurantSearchModels.get(position).getName() != null &&
                    !mRestaurantSearchModels.get(position).getName().equals("")) {
                holder.txtRestaurantNameView.setText(mRestaurantSearchModels.get(position).getName());
            }
            /*if (mRestaurantSearchModels.get(position).getRating() != null &&
                    !mRestaurantSearchModels.get(position).getRating().equals("")) {
                holder.ratingBarRatingsView.setRating(
                        Float.parseFloat(mRestaurantSearchModels.get(position).getRating()));
                holder.ratingBarRatingsView.setIndicator(true);
                holder.txtNumberOfReviewView.setText(mRestaurantModels.get(position).getRating());
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantSearchModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgRestaurantItemView;
        //        private final TextView txtAverageCostView;
        private final TextView txtLocalityView;
        private final TextView txtCuisinesView;
        private final TextView txtRestaurantNameView;
        private final RatingBar ratingBarRatingsView;
//        private final TextView txtNumberOfReviewView;

        public ViewHolder(View itemView) {
            super(itemView);

            imgRestaurantItemView = itemView.findViewById(R.id.imgRestaurantItemView);
//            txtAverageCostView = itemView.findViewById(R.id.txtAverageCostView);
            txtLocalityView = itemView.findViewById(R.id.txtLocalityView);
            txtCuisinesView = itemView.findViewById(R.id.txtCuisinesView);
            txtRestaurantNameView = itemView.findViewById(R.id.txtRestaurantNameView);
            ratingBarRatingsView = itemView.findViewById(R.id.ratingBarRatingsView);
//            txtNumberOfReviewView = itemView.findViewById(R.id.txtNumberOfReviewView);

        }
    }
}
