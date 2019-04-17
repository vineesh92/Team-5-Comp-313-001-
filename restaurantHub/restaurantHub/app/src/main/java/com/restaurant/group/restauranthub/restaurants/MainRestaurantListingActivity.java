package com.restaurant.manasa.restauranthub.ui.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.AppController;
import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.Restaurant;
import com.restaurant.manasa.restauranthub.models.RestaurantModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantListingContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.location_search.LocationSearchActivity;
import com.restaurant.manasa.restauranthub.ui.my_bookings.MyBookingsActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants_details.RestaurantsDetailsActivity;
import com.restaurant.manasa.restauranthub.ui.settings.SettingsActivity;
import com.restaurant.manasa.restauranthub.utils.BottomNavigationViewBehaviour;
import com.restaurant.manasa.restauranthub.utils.RecyclerTouchListener;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import java.util.List;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;

public class MainRestaurantListingActivity extends BaseActivity implements RestaurantListingContract.View {
    private boolean doubleBackToExitPressedOnce = false;
    private RecyclerView mRecyclerViewRestaurants;
    private LinearLayout mLinearLayoutProgressbar;
    private RelativeLayout mRestaurantSearchEditTextLayout;
    private TextView mLocationTextView;
    private TextView mTitleText;
    private Toolbar mTopToolbar;
    private NestedScrollView mNestedScrollView;
    private RestaurantFeaturedItemAdapter mFeaturedItemAdapter;
    private RecyclerView mFeaturedItemRecyclerView;
    private LinearLayout mMainLinearLayout;
    private BottomNavigationView mBottomNavigationView;
    private RestaurantListingPresenter mRestaurantListingPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_listing);
        initUi();

    }

    private void initUi() {

        if (Utils.isNetworkAvailable(this)) {
            mTopToolbar = findViewById(R.id.toolBar);
            mTopToolbar.setNavigationIcon(null);
            setSupportActionBar(mTopToolbar);

            mLinearLayoutProgressbar = findViewById(R.id.linear_layout_progressbar);
            mRestaurantSearchEditTextLayout = findViewById(R.id.relayoutSourceLocationInput);
            mTitleText = findViewById(R.id.title_text);
            mTitleText.setText(AppController.APP_TITLE);
            mRecyclerViewRestaurants = findViewById(R.id.recycler_view_restaurant_list);
            mFeaturedItemRecyclerView = findViewById(R.id.recycler_featured_items);
            mMainLinearLayout = findViewById(R.id.layout_restaurant_listing);
            mBottomNavigationView = findViewById(R.id.bottom_navigation);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
            layoutParams.setBehavior(new BottomNavigationViewBehaviour());
            mNestedScrollView = findViewById(R.id.scrollView_restaurantListing);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                    MainRestaurantListingActivity.this);
            mRecyclerViewRestaurants.setLayoutManager(layoutManager);

            mLinearLayoutProgressbar.setVisibility(View.VISIBLE);

            mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Intent intent;
                    switch (item.getItemId()) {

                        case R.id.navigation_home:


                            break;

                        case R.id.navigation_bookings:

                            if (Utils.isNetworkAvailable(MainRestaurantListingActivity.this)) {
                                intent = new Intent(MainRestaurantListingActivity.this, MyBookingsActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                showSnackBar();
                            }
                            break;

                        case R.id.navigation_account:

                            if (Utils.isNetworkAvailable(MainRestaurantListingActivity.this)) {
                                intent = new Intent(MainRestaurantListingActivity.this, SettingsActivity.class);
                                startActivityForResult(intent, 1);
                                overridePendingTransition(R.anim.right, R.anim.left);
                            } else {
                                showSnackBar();
                            }
                            break;
                    }
                    return true;
                }
            });

            mRestaurantListingPresenter = new RestaurantListingPresenter(new RestaurantInteractor(),
                    MainRestaurantListingActivity.this);

            String token = SharedPrefsUtils.getStringPreference(MainRestaurantListingActivity.this,
                    KEY_TOKEN, "");

            mRestaurantListingPresenter.requestRestaurantListData(token);

            mRestaurantSearchEditTextLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isNetworkAvailable(MainRestaurantListingActivity.this)) {
                        Intent intent = new Intent(MainRestaurantListingActivity.this, LocationSearchActivity.class);
                        startActivityForResult(intent, 2);
                        overridePendingTransition(R.anim.right, R.anim.left);
                    } else showSnackBar();
                }
            });

            mLocationTextView = findViewById(R.id.restaurentSearchEditText);
        }
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    @Override
    public void showProgress() {
        mLinearLayoutProgressbar.setVisibility(View.VISIBLE);
        mRestaurantSearchEditTextLayout.setVisibility(View.GONE);
        mMainLinearLayout.setVisibility(View.GONE);
        mTopToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mLinearLayoutProgressbar.setVisibility(View.GONE);
        mRestaurantSearchEditTextLayout.setVisibility(View.VISIBLE);
        mMainLinearLayout.setVisibility(View.VISIBLE);
        mTopToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        // Failure condition

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadRestaurantList(RestaurantModel restaurantModels) {
        //Handle mAdapterRestaurantListing notify here
        if (restaurantModels != null && restaurantModels.getResult() != null &&
                restaurantModels.getResult().getRestaurants() != null &&
                restaurantModels.getResult().getRestaurants().size() > 0) {
            RestaurantListingAdapter mAdapterRestaurantListing = new RestaurantListingAdapter(MainRestaurantListingActivity.this,
                    restaurantModels.getResult().getRestaurants());
            mRecyclerViewRestaurants.setNestedScrollingEnabled(false);
            mRecyclerViewRestaurants.setAdapter(mAdapterRestaurantListing);

            mRecyclerViewRestaurants.addOnItemTouchListener(new RecyclerTouchListener(MainRestaurantListingActivity.this, mRecyclerViewRestaurants, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if (Utils.isNetworkAvailable(MainRestaurantListingActivity.this)) {
                        Intent intent = new Intent(MainRestaurantListingActivity.this, RestaurantsDetailsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right, R.anim.left);
                    } else {
                        showSnackBar();
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

        //TODO : Redesign New Function Call
        setFeaturedItems(restaurantModels.getResult().getRestaurants());
    }

    private void setFeaturedItems(List<Restaurant> restaurant) {

        mFeaturedItemAdapter = new RestaurantFeaturedItemAdapter(MainRestaurantListingActivity.this, restaurant);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFeaturedItemRecyclerView.setLayoutManager(llm);
        mFeaturedItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFeaturedItemRecyclerView.setAdapter(mFeaturedItemAdapter);

        mFeaturedItemRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainRestaurantListingActivity.this, mFeaturedItemRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (Utils.isNetworkAvailable(MainRestaurantListingActivity.this)) {
                    Intent intent = new Intent(MainRestaurantListingActivity.this, RestaurantsDetailsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else {
                    showSnackBar();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Utils.stylableToast(MainRestaurantListingActivity.this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        if (mNestedScrollView != null)
            mNestedScrollView.scrollTo(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data.getStringExtra("logout") != null) finish();
        else if (resultCode == RESULT_OK && data.getStringExtra("exit") != null) {
            MainRestaurantListingActivity.this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}