package com.restaurant.manasa.restauranthub.ui.booking_confirmed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.squareup.picasso.Picasso;

public class BookingConfirmedActivity extends BaseActivity {

    private MyBookingsItem mMyBookingsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);

        if (getIntent().getSerializableExtra("MyBookingsList") != null)
            mMyBookingsItem = (MyBookingsItem) getIntent().getSerializableExtra("MyBookingsList");

        iniUI();

    }

    private void iniUI() {

        android.support.v7.widget.Toolbar toolBar = findViewById(R.id.toolBar);

        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        findViewById(R.id.button_done_bc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        });

        TextView mRestaurantNameTextView = findViewById(R.id.text_restaurant_name_bc);
        TextView mBookingIdTextView = findViewById(R.id.text_booking_id_bc);
        TextView mNoOfPeopleTextView = findViewById(R.id.text_no_of_guests_bc);
        TextView mTimeTextView = findViewById(R.id.text_time_bc);
        TextView mDateTextView = findViewById(R.id.text_date_bc);
        ImageView mRestaurantImageView = findViewById(R.id.image_booking_confirmed);

        mRestaurantNameTextView.setText(mMyBookingsItem.getRestaurantName());
        mBookingIdTextView.setText(mMyBookingsItem.getBookingId());
        mNoOfPeopleTextView.setText(mMyBookingsItem.getPeople());
        mDateTextView.setText(mMyBookingsItem.getDate());
        mTimeTextView.setText(mMyBookingsItem.getTime());

        Picasso.get()
                .load(mMyBookingsItem.getImage())
                .placeholder(R.drawable.ic_placeholder)
                .into(mRestaurantImageView);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
