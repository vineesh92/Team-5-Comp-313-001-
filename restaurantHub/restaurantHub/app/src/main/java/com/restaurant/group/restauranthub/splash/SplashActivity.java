package com.restaurant.manasa.restauranthub.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.ui.error_view.ErrorActivity;
import com.restaurant.manasa.restauranthub.ui.login_or_register.LoginOrRegisterActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants.MainRestaurantListingActivity;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;



public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToHomePage();
            }
        }, 3000);

    }

    private void goToHomePage() {

        String token = SharedPrefsUtils.getStringPreference(
                SplashActivity.this, KEY_TOKEN, "");
        if (!Utils.isNetworkAvailable(SplashActivity.this)) {

            Intent intent = new Intent(SplashActivity.this, ErrorActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right, R.anim.left);
            SplashActivity.this.finish();

        } else if (token != null && token.length() > 0) {

            Intent intentRestaurentList = new Intent(SplashActivity.this,
                    MainRestaurantListingActivity.class);
            startActivity(intentRestaurentList);
            overridePendingTransition(R.anim.right, R.anim.left);
            SplashActivity.this.finish();

        } else {
            Intent intent = new Intent(SplashActivity.this, LoginOrRegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right, R.anim.left);
            SplashActivity.this.finish();
        }
    }
}