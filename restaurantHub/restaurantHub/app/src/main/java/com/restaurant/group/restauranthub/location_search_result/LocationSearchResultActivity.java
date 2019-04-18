package com.restaurant.manasa.restauranthub.ui.location_search_result;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.RestaurantModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantListingContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.location_search.LocationSearchActivity;
import com.restaurant.manasa.restauranthub.ui.my_bookings.MyBookingsActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants.MainRestaurantListingActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants.RestaurantInteractor;
import com.restaurant.manasa.restauranthub.ui.restaurants_details.RestaurantsDetailsActivity;
import com.restaurant.manasa.restauranthub.ui.settings.SettingsActivity;
import com.restaurant.manasa.restauranthub.utils.BottomNavigationViewBehaviour;
import com.restaurant.manasa.restauranthub.utils.RecyclerTouchListener;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;

public class LocationSearchResultActivity extends BaseActivity implements RestaurantListingContract.View {

    private TextView mLocationTextView;
    private String mLocation;
    private BottomNavigationView mBottomNavigationView;
    private RelativeLayout mRestaurantSearchEditTextLayout;
    private LocationSearchResultPresenter mRestaurantListingPresenter;
    private RecyclerView mRecyclerViewSearchRestaurants;
    private LinearLayout mLinearLayoutProgressbar;
    private LinearLayout mMainLinearLayout;
    private Toolbar mTopToolbar;

    NestedScrollView nestedScrollView;

    private NestedScrollView mScrollView;


    int previousScrollY = 0;
    private int scrollHeight = 0;
    private int scrollUppreviousValue = 0;
    private int consecutiveUpScrollCount;
    private int consecutiveDownScrollCount = 0;
    private int scrollDownPreviousValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_result);

        if (getIntent().getStringExtra("Location") != null) {

            mLocation = getIntent().getStringExtra("Location");
        }

        initUi();
    }

    private void initUi() {

        mLocationTextView = findViewById(R.id.restaurentSearchEditText);
        mLocationTextView.setText(mLocation);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());
        mRestaurantSearchEditTextLayout = findViewById(R.id.relayoutSourceLocationInput);
        mRecyclerViewSearchRestaurants = findViewById(R.id.recycler_view_search_restaurant_list);
        mLinearLayoutProgressbar = findViewById(R.id.linear_layout_progressbar);
//        mMainLinearLayout = findViewById(R.id.layout_restaurant_search_listing);
        mTopToolbar = findViewById(R.id.toolBar);
//        mScrollView = findViewById(R.id.scrollView_restaurantSearchListing);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                LocationSearchResultActivity.this);
        mRecyclerViewSearchRestaurants.setLayoutManager(layoutManager);

        nestedScrollView = findViewById(R.id.scrollView_restaurantSearchListing);

//
//        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//
////                if (nestedScrollView.getScrollY() > previousScrollY && mTopToolbar.getVisibility() == View.VISIBLE) {
////                    mTopToolbar.setVisibility(View.GONE);
////                } else if (nestedScrollView.getScrollY() < previousScrollY && mTopToolbar.getVisibility() != View.VISIBLE) {
////                    mTopToolbar.setVisibility(View.VISIBLE);
////                }
////                previousScrollY = nestedScrollView.getScrollY();
//
//
//                int scrollY = nestedScrollView.getScrollY(); // For ScrollView
//                int scrollX = nestedScrollView.getScrollX(); // For HorizontalScrollView
//                if (scrollY > scrollHeight + 30 && (scrollY - scrollUppreviousValue) > 50) {
//                    consecutiveUpScrollCount = 0;
//                    consecutiveDownScrollCount++;
//                    if (consecutiveDownScrollCount > 5) {
//                        scrollDownPreviousValue = scrollY;
//                        Log.d("SCROLL DIRECTION", "DOWN" + scrollY);
//
//                        mTopToolbar.setVisibility(View.GONE);
//                    }
//                    scrollHeight = scrollY;
//                }
//                if (scrollY < scrollHeight - 30 && (scrollDownPreviousValue - scrollY) > 50) {
//                    consecutiveDownScrollCount = 0;
//                    consecutiveUpScrollCount++;
//                    if (consecutiveUpScrollCount > 5 || nestedScrollView.getScrollY()==0) {
//                        scrollUppreviousValue = scrollY;
//                        Log.d("SCROLL DIRECTION", "UP" + scrollY);
//                        mTopToolbar.setVisibility(View.VISIBLE);
//
//                    }
//                    scrollHeight = scrollY;
//                }
//            }
//
//        });

        mBottomNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mBottomNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.ratingColor)));
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        if (Utils.isNetworkAvailable(LocationSearchResultActivity.this)) {
                            intent = new Intent(LocationSearchResultActivity.this, MainRestaurantListingActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        } else {
                            showSnackBar();
                        }
                        break;

                    case R.id.navigation_bookings:

                        if (Utils.isNetworkAvailable(LocationSearchResultActivity.this)) {
                            intent = new Intent(LocationSearchResultActivity.this, MyBookingsActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        } else {
                            showSnackBar();
                        }
                        break;

                    case R.id.navigation_account:

                        if (Utils.isNetworkAvailable(LocationSearchResultActivity.this)) {
                            intent = new Intent(LocationSearchResultActivity.this, SettingsActivity.class);
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

        mRestaurantSearchEditTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(LocationSearchResultActivity.this)) {
                    Intent intent = new Intent(LocationSearchResultActivity.this, LocationSearchActivity.class);
                    intent.putExtra("isBackEnabled", true);
                    startActivityForResult(intent, 2009);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
            }
        });

        mRestaurantListingPresenter = new LocationSearchResultPresenter(new RestaurantInteractor(),
                LocationSearchResultActivity.this);

        String token = SharedPrefsUtils.getStringPreference(LocationSearchResultActivity.this,
                KEY_TOKEN, "");

        mRestaurantListingPresenter.requestRestaurantListData(token);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Utils.stylableToast(LocationSearchResultActivity.this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void showProgress() {

        mRestaurantSearchEditTextLayout.setVisibility(View.GONE);
//        mMainLinearLayout.setVisibility(View.GONE);
        mTopToolbar.setVisibility(View.GONE);
        mLinearLayoutProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        mLinearLayoutProgressbar.setVisibility(View.GONE);
        mRestaurantSearchEditTextLayout.setVisibility(View.VISIBLE);
//        mMainLinearLayout.setVisibility(View.VISIBLE);
        mTopToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onLoadRestaurantList(RestaurantModel arrayList) {


        if (arrayList != null && arrayList.getResult() != null &&
                arrayList.getResult().getRestaurants() != null &&
                arrayList.getResult().getRestaurants().size() > 0) {
            LocationSearchResultAdapter mLocationSearchResultAdapter = new LocationSearchResultAdapter(LocationSearchResultActivity.this,
                    arrayList.getResult().getRestaurants());
            mRecyclerViewSearchRestaurants.setNestedScrollingEnabled(false);
            mRecyclerViewSearchRestaurants.setAdapter(mLocationSearchResultAdapter);

            mRecyclerViewSearchRestaurants.addOnItemTouchListener(new RecyclerTouchListener(LocationSearchResultActivity.this, mRecyclerViewSearchRestaurants, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if (Utils.isNetworkAvailable(LocationSearchResultActivity.this)) {
                        Intent intent = new Intent(LocationSearchResultActivity.this, RestaurantsDetailsActivity.class);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2009) {
            if (data != null && data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                if (bundle.containsKey("Location")) {

                    mLocation = bundle.getString("Location", "");
                    mLocationTextView.setText(mLocation);
//                    String token = SharedPrefsUtils.getStringPreference(LocationSearchResultActivity.this,
//                            KEY_TOKEN, "");
//                    mRestaurantListingPresenter.requestRestaurantListData(token);

                }
            }
        }
    }
}
