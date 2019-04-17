package com.restaurant.manasa.restauranthub.ui.facility_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.Facility;

import java.util.List;



public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.MyViewHolder> {


    private final List<Facility> mFacilities;


    public FacilityAdapter(List<Facility> facilities) {

        mFacilities = facilities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_facilities_single_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if (mFacilities != null && mFacilities.get(position).getFacility() != null) {
            holder.mTextViewRoomFeature.setText(mFacilities.get(position).getFacility());
            if(position == 0){
                holder.mImageRoomFacilities.setImageResource(R.drawable.ic_vehicle_car);
            } else {
                holder.mImageRoomFacilities.setImageResource(R.drawable.ic_wifi_new);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFacilities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextViewRoomFeature;
        final ImageView mImageRoomFacilities;

        MyViewHolder(View view) {
            super(view);

            mTextViewRoomFeature = view.findViewById(R.id.textViewFeatures);
            mImageRoomFacilities = view.findViewById(R.id.image_facilities);


        }
    }
}