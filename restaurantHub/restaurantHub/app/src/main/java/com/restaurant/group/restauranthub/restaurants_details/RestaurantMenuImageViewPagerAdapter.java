package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MenuImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RestaurantMenuImageViewPagerAdapter extends PagerAdapter {

    private final ArrayList<MenuImage> IMAGES;
    private final LayoutInflater inflater;


    public RestaurantMenuImageViewPagerAdapter(Context context,ArrayList<MenuImage> IMAGES) {
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_restaurant_menu_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.restaurant_details_menu_image);


        Picasso.get()
                .load(IMAGES.get(position).getMenuImage())
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
