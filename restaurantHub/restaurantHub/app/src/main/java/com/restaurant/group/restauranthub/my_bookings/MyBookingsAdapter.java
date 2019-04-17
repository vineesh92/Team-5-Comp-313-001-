package com.restaurant.manasa.restauranthub.ui.my_bookings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.booking_confirmed.BookingConfirmedActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyViewHolder> {

    private final Activity mContext;
    private final ArrayList<MyBookingsItem> mMyBookingsList;


    MyBookingsAdapter(Activity context, ArrayList<MyBookingsItem> mSimilarRestaurantsList) {
        this.mContext = context;
        this.mMyBookingsList = mSimilarRestaurantsList;
    }

    @NonNull
    @Override
    public MyBookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mybookings, parent, false);
        return new MyBookingsAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull MyBookingsAdapter.MyViewHolder holder, final int position) {

        Picasso.get()
                .load(mMyBookingsList.get(position).getImage())
                .into(holder.mRestaurantImage);

        //holder.mBookingId.setText("Booking Id : "+mMyBookingsList.get(position).getBookingId());
        holder.mRestaurantName.setText(mMyBookingsList.get(position).getRestaurantName());
        //holder.mPeople.setText("No of People : "+mMyBookingsList.get(position).getPeople());
        holder.mDate.setText(mMyBookingsList.get(position).getDate());
        //holder.mTime.setText(mMyBookingsList.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(mContext)) {

                    Intent intent = new Intent(mContext, BookingConfirmedActivity.class);
                    intent.putExtra("MyBookingsList", mMyBookingsList.get(position));
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.right, R.anim.left);

                } else {
                    ((BaseActivity) mContext).showSnackBar();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMyBookingsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView mRestaurantImage;
        final TextView mBookingId;
        final TextView mRestaurantName;
        final TextView mPeople;
        final TextView mDate;
        final TextView mTime;

        MyViewHolder(View view) {
            super(view);

            mRestaurantImage = view.findViewById(R.id.image_mybookings);
            mBookingId = view.findViewById(R.id.text_booking_id);
            mRestaurantName = view.findViewById(R.id.text_restaurant_name);
            mPeople = view.findViewById(R.id.text_no_of_people);
            mDate = view.findViewById(R.id.text_date);
            mTime = view.findViewById(R.id.text_time);
        }
    }
}
