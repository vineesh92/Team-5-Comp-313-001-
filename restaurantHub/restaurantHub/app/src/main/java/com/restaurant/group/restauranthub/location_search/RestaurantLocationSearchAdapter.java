package com.restaurant.manasa.restauranthub.ui.location_search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.PlaceAPISearchResultModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RestaurantLocationSearchAdapter extends RecyclerView.Adapter<RestaurantLocationSearchAdapter.ViewHolder> {

    private static ArrayList<PlaceAPISearchResultModel> mListPlace = new ArrayList<>();
    private static ArrayList<PlaceAPISearchResultModel> mMergedListPlace = new ArrayList<>();
    private String mKeyWord = "";
    private final Context mContext;
    private OnLoacationSelectedListener mListener;

    void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView locationName, locationDetails;

        ViewHolder(View v) {
            super(v);
            locationName = v.findViewById(R.id.text_location_name);
            locationDetails = v.findViewById(R.id.text_location_description);
        }
    }

    RestaurantLocationSearchAdapter(Context context, ArrayList<PlaceAPISearchResultModel> listPlace) {

        mListPlace = listPlace;
        mMergedListPlace = listPlace;
        mContext = context;

    }

    @NonNull
    @Override
    public RestaurantLocationSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View mRootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_location_search_result, viewGroup, false);
        mListener = (OnLoacationSelectedListener) mContext;
        return new RestaurantLocationSearchAdapter.ViewHolder(mRootView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final RestaurantLocationSearchAdapter.ViewHolder viewHolder, final int position) {

        if (mMergedListPlace.size() > 0) {
            String placeName = mMergedListPlace.get(position).description.toString();
            String secondLine = mMergedListPlace.get(position).secondLine.toString();

            SpannableStringBuilder sb = new SpannableStringBuilder(placeName);

            if (mKeyWord.length() > 0) {

                Pattern p = Pattern.compile(mKeyWord, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(placeName);
                while (m.find()) {

                    sb.setSpan(new ForegroundColorSpan(Color.parseColor("#909090")), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }

            Log.e("position", "" + position);
            viewHolder.locationName.setText(sb);
            viewHolder.locationDetails.setText(secondLine);
            viewHolder.locationDetails.setVisibility(View.VISIBLE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLocationSelected(mMergedListPlace.get(position).description.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        // return mListPlace.size();
        int size;
        size = mMergedListPlace.size();
        return size;

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public interface OnLoacationSelectedListener {

        void onLocationSelected(String location);
    }
}