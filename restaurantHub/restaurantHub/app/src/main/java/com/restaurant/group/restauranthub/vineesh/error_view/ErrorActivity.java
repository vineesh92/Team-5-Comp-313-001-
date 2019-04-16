package com.restaurant.manasa.restauranthub.ui.error_view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.ui.restaurants.MainRestaurantListingActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initUi();
    }

    private void initUi() {


        TextView mButton = findViewById(R.id.ev_retry);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isNetworkAvailable(ErrorActivity.this)) {

                    Intent intent = new Intent(ErrorActivity.this, MainRestaurantListingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                    ErrorActivity.this.finish();

                } else {

                    final Animation animShake = AnimationUtils.loadAnimation(ErrorActivity.this, R.anim.shake);
                    findViewById(R.id.container_error_view).startAnimation(animShake);
                }
            }
        });
    }
}
