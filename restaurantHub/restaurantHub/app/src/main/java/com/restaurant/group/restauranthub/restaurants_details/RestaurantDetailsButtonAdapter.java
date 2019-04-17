package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.SelectionButton;

import java.util.List;


public class RestaurantDetailsButtonAdapter extends RecyclerView.Adapter<RestaurantDetailsButtonAdapter.MyViewHolder> {

    private List<SelectionButton> mTimeList;
    private Context mContext;

    RestaurantDetailsButtonAdapter(List<SelectionButton> mTimeList) {
        this.mTimeList = mTimeList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Button mTimeButton;

        MyViewHolder(View itemView) {
            super(itemView);
            mTimeButton = itemView.findViewById(R.id.button_item_time);
        }
    }

    @NonNull
    @Override
    public RestaurantDetailsButtonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_button, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDetailsButtonAdapter.MyViewHolder holder, int position) {

        if (mTimeList.get(position).isSelected()) {

            holder.mTimeButton.setBackground(mContext.getResources().getDrawable(R.drawable.button_selected));
            holder.mTimeButton.setTextColor(Color.parseColor("#6F6F6F"));
        }
        else{
            holder.mTimeButton.setBackground(mContext.getResources().getDrawable(R.drawable.bg_wallet_button_dark_accent));
            holder.mTimeButton.setTextColor(mContext.getResources().getColor(R.color.pageColorWhite));
        }
        holder.mTimeButton.setText(mTimeList.get(position).getmText());
    }

    @Override
    public int getItemCount() {
        return mTimeList.size();
    }
}
