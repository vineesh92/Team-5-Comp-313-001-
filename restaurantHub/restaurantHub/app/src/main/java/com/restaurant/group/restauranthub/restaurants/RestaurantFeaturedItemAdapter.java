package com.restaurant.manasa.restauranthub.ui.restaurants;

import android.app.Activity;
import android.content.Intent;
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
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants_details.RestaurantsDetailsActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RestaurantFeaturedItemAdapter extends RecyclerView.Adapter<RestaurantFeaturedItemAdapter.ViewHolder> {


    private final Activity mContext;
    private final List<Restaurant> mRestaurantModels;

    RestaurantFeaturedItemAdapter(Activity context, List<Restaurant> restaurantModels) {
        mContext = context;
        mRestaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public RestaurantFeaturedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_item, parent, false);

        return new RestaurantFeaturedItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFeaturedItemAdapter.ViewHolder holder, int position) {


        holder.mPaddingView.setVisibility(position == mRestaurantModels.size() - 1 ? View.VISIBLE : View.GONE);

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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNetworkAvailable(mContext)) {
                        Intent intent = new Intent(mContext, RestaurantsDetailsActivity.class);
                        mContext.startActivity(intent);
                        mContext.overridePendingTransition(R.anim.right, R.anim.left);
                    } else {

                        ((BaseActivity) mContext).showSnackBar();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgRestaurantItemView;
        //        private final TextView txtAverageCostView;
        private final TextView txtLocalityView;
        private final TextView txtCuisinesView;
        private final TextView txtRestaurantNameView;
        private final RatingBar ratingBarRatingsView;
        private View mPaddingView;
//        private final TextView txtNumberOfReviewView;

        ViewHolder(View view) {
            super(view);

            imgRestaurantItemView = view.findViewById(R.id.imgRestaurantItemView);
//            txtAverageCostView = view.findViewById(R.id.txtAverageCostView);
            txtLocalityView = view.findViewById(R.id.txtLocalityView);
            txtCuisinesView = view.findViewById(R.id.txtCuisinesView);
            txtRestaurantNameView = view.findViewById(R.id.txtRestaurantNameView);
            ratingBarRatingsView = view.findViewById(R.id.ratingBarRatingsView);
            mPaddingView = itemView.findViewById(R.id.paddingView);
//            txtNumberOfReviewView = view.findViewById(R.id.txtNumberOfReviewView);
        }
    }
}
