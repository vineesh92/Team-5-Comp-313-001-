package com.restaurant.manasa.restauranthub.ui.menu_fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MenuImage;
import com.restaurant.manasa.restauranthub.ui.restaurants_details.RestaurantMenuImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RestaurantDetailsMenuDialog extends DialogFragment {

    public RestaurantDetailsMenuDialog() {
    }

    public static RestaurantDetailsMenuDialog newInstance(List<MenuImage> menuImages, int position) {
        RestaurantDetailsMenuDialog fragment = new RestaurantDetailsMenuDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("Menu_Images", (ArrayList<? extends Parcelable>) menuImages);
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_restaurant_details_menu_dialog, container, false);
        ViewPager viewPager = mRootView.findViewById(R.id.view_pager_dialog);
        assert getArguments() != null;
        viewPager.setAdapter(new RestaurantMenuImageViewPagerAdapter(getContext(), getArguments().<MenuImage>getParcelableArrayList("Menu_Images")));
        viewPager.setCurrentItem(getArguments().getInt("Position"));
        ImageView mCloseButton = mRootView.findViewById(R.id.button_menu_fragment_close);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }
}