package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.customViews.LockableViewPager;
import com.restaurant.manasa.restauranthub.models.Facility;
import com.restaurant.manasa.restauranthub.models.MenuImage;
import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.models.RestaurantDetailsDineOutRestaurantTimeSlotsModel;
import com.restaurant.manasa.restauranthub.models.RestaurantDetailsModel;
import com.restaurant.manasa.restauranthub.models.SelectionButton;
import com.restaurant.manasa.restauranthub.mvp.RestaurantDetailsContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.booking_confirmed.BookingConfirmedActivity;
import com.restaurant.manasa.restauranthub.ui.facility_fragment.FacilityAdapter;
import com.restaurant.manasa.restauranthub.ui.menu_fragments.RestaurantDetailsMenuDialog;
import com.restaurant.manasa.restauranthub.utils.AppConstants;
import com.restaurant.manasa.restauranthub.utils.RecyclerTouchListener;
import com.restaurant.manasa.restauranthub.utils.RunTimePermission;
import com.restaurant.manasa.restauranthub.utils.RuntimePermissionInterface;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;


public class RestaurantsDetailsActivity extends BaseActivity implements RestaurantDetailsContract.View,
        OnMapReadyCallback, View.OnClickListener, RuntimePermissionInterface,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final ArrayList<RestaurantDetailsDineOutRestaurantTimeSlotsModel> mTimeSlots = new ArrayList<>();

    private ImageView mImageViewRestaurantDetailsImage;
    private TextView mRaTextViewRestaurantName;
    private TextView mRaTextViewRestaurantDetailsType;
    private TextView mRaTextViewRestaurantLocation;
    private TextView mRaTextViewCount;
    private RatingBar mRatingBarRestaurantDetails;
    private TextView mRaTextViewRestaurantDetailsText;
    private TextView mRestaurantRate;
    private ViewPager mViewPagerRestaurantDetailsImage;
    private int mFeatureImageCount = 0;

    private GoogleMap googleMap;
    private String mRestaurantName, mRestaurantLocation;

    private String mLat;
    private String mLong;
    private LinearLayout linearProgressbar;
    private LockableViewPager mViewPagerNew;
    private List<Facility> facility = new ArrayList<>();
    private List<MenuImage> menuImage = new ArrayList<>();

    private RecyclerView mSimilarRestaurantsRecyclerView;

    private RestaurantDetailsImageAdapter mRestaurantDetailsImageAdapter;
    private RecyclerView mRestaurantDetailsImageRecycler;
    private RecyclerView mTimeRecycler;
    private RecyclerView mDateRecycler;
    private RecyclerView mPeopleRecycler;
    private RestaurantDetailsButtonAdapter mTimeButtonAdapter;
    private RestaurantDetailsButtonAdapter mDateButtonAdapter;
    private RestaurantDetailsButtonAdapter mPeopleButtonAdapter;
    private List<SelectionButton> mTimeList = new ArrayList<>();
    private List<SelectionButton> mDateList = new ArrayList<>();
    private List<SelectionButton> mPeopleList = new ArrayList<>();
    private Toolbar mToolBar;
    private RestaurantMenuAdapter mMenuAdapter;
    private RecyclerView mMenuRecyclerView;
    private RecyclerView mFacilitiesRecyclerView;
    private FacilityAdapter mFacilitiesAdapter;
    private TextView mSelectTimeLabel;
    private TextView mSelectPeopleLabel;
    private String selectedDate, selectedTime, selectedPeople;
    private boolean isTimeSelected = false, isDateSelected = false, isPeopleSelected = false;
    private String mRestaurantImageUrl;
    private AppBarLayout mAppbarLayout;
    private CollapsingToolbarLayout mCollapsingToolBar;
    private TextView mToolBarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_booking_details);

        initUi();

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        //checkPermissions();

        RestaurantDetailsPresenter mRestaurantDetailsPresenter = new RestaurantDetailsPresenter(new RestaurantDetailsInteractor(),
                this);
        String token = SharedPrefsUtils.getStringPreference(RestaurantsDetailsActivity.this,
                KEY_TOKEN, "");
        mRestaurantDetailsPresenter.requestRestaurantDetails(token);


    }

    private void checkPermissions() {
        RunTimePermission permission = new RunTimePermission(RestaurantsDetailsActivity.this, this);
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        permission.runtimePermissions(permissionList);
    }

    private void initUi() {

        linearProgressbar = findViewById(R.id.linearProgressbar);
        Button mRaButtonBookNow = findViewById(R.id.ipButtonBookNow);
        mRaTextViewRestaurantDetailsText = findViewById(R.id.ipTextViewRestaurantDetailsText);
        mRaTextViewRestaurantName = findViewById(R.id.ipTextViewRestaurantName);
        mRaTextViewRestaurantLocation = findViewById(R.id.ipTextViewRestaurantLocation);
        mRatingBarRestaurantDetails = findViewById(R.id.ratingBarRestarantDetails);
        mSelectTimeLabel = findViewById(R.id.label_select_time);
        mSelectPeopleLabel = findViewById(R.id.label_no_of_people);
        mToolBar = findViewById(R.id.toolBar);
        mAppbarLayout = findViewById(R.id.appBar);
        mCollapsingToolBar = findViewById(R.id.collapsing_toolbar);
        mToolBar.bringToFront();
        mToolBarText = findViewById(R.id.tv_title);
        mToolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
//        TabLayout mTabLayout = findViewById(R.id.restaurantBookingTab);
//        mTabLayout.setupWithViewPager(mViewPagerNew);
        mSimilarRestaurantsRecyclerView = findViewById(R.id.recycler_similar_restaurants);

        mRestaurantDetailsImageRecycler = findViewById(R.id.recycler_restaurant_images);
        mTimeRecycler = findViewById(R.id.recycler_button_time);
        mDateRecycler = findViewById(R.id.recycler_button_date);
        mPeopleRecycler = findViewById(R.id.recycler_button_people);
        mMenuRecyclerView = findViewById(R.id.recycler_menus);
        mFacilitiesRecyclerView = findViewById(R.id.recycler_facilities);

        // TODO: Disable swipe viewpager\
//        mViewPagerNew.setCanScroll(false);

        mRaButtonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                showBookingOptionDialog();
                if (Utils.isNetworkAvailable(RestaurantsDetailsActivity.this)) {

                    if (isDateSelected && isTimeSelected && isPeopleSelected) {
                        MyBookingsItem item = new MyBookingsItem();
                        item.setBookingId("12345678");
                        item.setDate(selectedDate);
                        item.setPeople(selectedPeople);
                        item.setTime(selectedTime);
                        item.setRestaurantName(mRestaurantName);
                        item.setImage(mRestaurantImageUrl);
                        Intent intent = new Intent(RestaurantsDetailsActivity.this, BookingConfirmedActivity.class);
                        intent.putExtra("MyBookingsList", item);
                        startActivityForResult(intent, 2);
                        overridePendingTransition(R.anim.right, R.anim.left);
                        RestaurantsDetailsActivity.this.finish();
                    } else if(isDateSelected&&isTimeSelected) {
                        Toast.makeText(RestaurantsDetailsActivity.this, "Please provide number of guests", Toast.LENGTH_SHORT).show();
                    }else if(isDateSelected){
                        Toast.makeText(RestaurantsDetailsActivity.this, "Please select time and number of people", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RestaurantsDetailsActivity.this, "Please select date, time and number of people", Toast.LENGTH_SHORT).show();
                    }



                } else showSnackBar();
            }
        });

        ToolBarAction();


    }

    private void ToolBarAction() {

        mToolBar.setTitle("");
        mCollapsingToolBar.setTitleEnabled(false);
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isVisible = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mToolBarText.setVisibility(View.VISIBLE);
                    isVisible = true;
                } else if(isVisible) {
                    mToolBarText.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });

    }


    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    @Override
    public void showProgress() {
        findViewById(R.id.appBar).setVisibility(View.GONE);
        findViewById(R.id.layout_container).setVisibility(View.GONE);
        linearProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        findViewById(R.id.appBar).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_container).setVisibility(View.VISIBLE);
        linearProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadRestaurantDetails(RestaurantDetailsModel restaurantDetailsModel) {


        if (restaurantDetailsModel != null && restaurantDetailsModel.getResult() != null) {

            if (restaurantDetailsModel.getResult().getName() != null &&
                    !restaurantDetailsModel.getResult().getName().equals("")) {
                mRaTextViewRestaurantName.setText(restaurantDetailsModel.getResult().getName());
                mRestaurantName = restaurantDetailsModel.getResult().getName();
                mToolBarText.setText(restaurantDetailsModel.getResult().getName());
            }
            if (restaurantDetailsModel.getResult().getLocality() != null &&
                    !restaurantDetailsModel.getResult().getLocality().equals("")) {
                mRaTextViewRestaurantLocation.setText(restaurantDetailsModel.getResult().getLocality());
                mRestaurantLocation = restaurantDetailsModel.getResult().getLocality();
            }
//            if (restaurantDetailsModel.getResult().getCuisines() != null &&
//                    !restaurantDetailsModel.getResult().getCuisines().equals("")) {
//                mRaTextViewRestaurantDetailsType.setText(restaurantDetailsModel.getResult().getCuisines());
//            }
//            if (restaurantDetailsModel.getResult().getAverageCostForTwo() != null &&
//                    !restaurantDetailsModel.getResult().getAverageCostForTwo().equals("")) {
//                mRestaurantRate.setText(getString(R.string.rs).concat(restaurantDetailsModel.getResult().
//                        getAverageCostForTwo()).concat(" for 2 people (approx)"));
//            }
            if (restaurantDetailsModel.getResult().getRating() != null &&
                    !restaurantDetailsModel.getResult().getRating().equals("")) {
                mRatingBarRestaurantDetails.setRating(Float.parseFloat(restaurantDetailsModel.getResult().getRating()));
            }
            if (restaurantDetailsModel.getResult().getRestaurantSummary() != null &&
                    !restaurantDetailsModel.getResult().getRestaurantSummary().equals("")) {
                mRaTextViewRestaurantDetailsText.setText(restaurantDetailsModel.getResult().getRestaurantSummary());
            }


            if (restaurantDetailsModel.getResult().getFeaturedImage() != null) {
                mFeatureImageCount = restaurantDetailsModel.getResult().getFeaturedImage().size();
                mRestaurantImageUrl = restaurantDetailsModel.getResult().getFeaturedImage().get(0).getFeaturedImage();
            }

            setImagesToAdapter(restaurantDetailsModel);

            setDateButton();


            if (restaurantDetailsModel.getResult().getLatitude() != null) {
                mLat = restaurantDetailsModel.getResult().getLatitude();
            }
            if (restaurantDetailsModel.getResult().getLongitude() != null) {
                mLong = restaurantDetailsModel.getResult().getLongitude();
            }

            if (googleMap != null) {
                LatLng location = new LatLng(Double.parseDouble(mLat),
                        Double.parseDouble(mLong));
                googleMap.addMarker(new MarkerOptions().position(location).
                        title(restaurantDetailsModel.getResult().getName()).
                        snippet(restaurantDetailsModel.getResult().getAddress()));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            if (restaurantDetailsModel.getResult().getFacility().size() > 0) {
                facility = restaurantDetailsModel.getResult().getFacility();
            }
            mFacilitiesAdapter = new FacilityAdapter(facility);
            LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mFacilitiesRecyclerView.setLayoutManager(llm);
            mFacilitiesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mFacilitiesRecyclerView.hasFixedSize();
            mFacilitiesRecyclerView.setAdapter(mFacilitiesAdapter);


            if (restaurantDetailsModel.getResult().getMenuImage().size() > 0) {
                menuImage = restaurantDetailsModel.getResult().getMenuImage();
            }

            mMenuAdapter = new RestaurantMenuAdapter(menuImage);
            llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mMenuRecyclerView.setLayoutManager(llm);
            mMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mMenuRecyclerView.hasFixedSize();
            mMenuRecyclerView.setAdapter(mMenuAdapter);

            mMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(RestaurantsDetailsActivity.this, mMenuRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    RestaurantDetailsMenuDialog dialog = RestaurantDetailsMenuDialog.newInstance(menuImage, position);
                    dialog.show(getSupportFragmentManager(), "");
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

//
//            setupViewPager(mViewPagerNew, facility, menuImage);

            SimilarRestaurantsAdapter mSimilarRestaurantsAdapter = new SimilarRestaurantsAdapter(this, restaurantDetailsModel.getResult().getSimilar_restaurants());
            mSimilarRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantsDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            mSimilarRestaurantsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mSimilarRestaurantsRecyclerView.hasFixedSize();
            mSimilarRestaurantsRecyclerView.setAdapter(mSimilarRestaurantsAdapter);

            mSimilarRestaurantsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(RestaurantsDetailsActivity.this, mSimilarRestaurantsRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if (Utils.isNetworkAvailable(RestaurantsDetailsActivity.this)) {
                        Intent intent = new Intent(RestaurantsDetailsActivity.this, RestaurantsDetailsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right, R.anim.left);
                        RestaurantsDetailsActivity.this.finish();
                    } else showSnackBar();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }
    }

    private void setTimeButton() {

        mSelectTimeLabel.setVisibility(View.VISIBLE);
        mTimeRecycler.setVisibility(View.VISIBLE);

        mTimeList.add(new SelectionButton("3:00 PM", false));
        mTimeList.add(new SelectionButton("3:30 PM", false));
        mTimeList.add(new SelectionButton("4:00 PM", false));
        mTimeList.add(new SelectionButton("4:30 PM", false));
        mTimeList.add(new SelectionButton("5:00 PM", false));
        mTimeList.add(new SelectionButton("5:30 PM", false));
        mTimeList.add(new SelectionButton("6:00 PM", false));
        mTimeList.add(new SelectionButton("6:30 PM", false));

        mTimeButtonAdapter = new RestaurantDetailsButtonAdapter(mTimeList);
        mTimeRecycler.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTimeRecycler.setLayoutManager(llm);
        mTimeRecycler.hasFixedSize();
        mTimeRecycler.setAdapter(mTimeButtonAdapter);

        mTimeRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, mTimeRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (position > -1) {
                    for (int i = 0; i < mTimeList.size(); i++) mTimeList.get(i).setSelected(false);
                    mTimeList.get(position).setSelected(true);
                    mTimeButtonAdapter.notifyDataSetChanged();
                    selectedTime = mTimeList.get(position).getmText();
                    setPeopleButton();
                    isTimeSelected = true;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setDateButton() {


       Date  date = new Date();
       for(int i = 0; i <= 5; i++) {
           Date date1 = Utils.getDate(date, "dd MMM", i);
           String mDate = Utils.convertDateToString(date1, "dd MMM");
           mDateList.add(new SelectionButton(mDate, false));
       }

        mDateButtonAdapter = new RestaurantDetailsButtonAdapter(mDateList);
        mDateRecycler.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDateRecycler.setLayoutManager(llm);
        mDateRecycler.hasFixedSize();
        mDateRecycler.setAdapter(mDateButtonAdapter);

        mDateRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, mDateRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (position > -1) {
                    for (int i = 0; i < mDateList.size(); i++) mDateList.get(i).setSelected(false);
                    mDateList.get(position).setSelected(true);
                    mDateButtonAdapter.notifyDataSetChanged();
                    selectedDate = mDateList.get(position).getmText();
                    isDateSelected = true;
                    setTimeButton();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setPeopleButton() {

        mSelectPeopleLabel.setVisibility(View.VISIBLE);
        mPeopleRecycler.setVisibility(View.VISIBLE);


        mPeopleList.add(new SelectionButton("Me Only", false));
        mPeopleList.add(new SelectionButton("Two", false));
        mPeopleList.add(new SelectionButton("Three", false));
        mPeopleList.add(new SelectionButton("Four", false));
        mPeopleList.add(new SelectionButton("Five", false));
        mPeopleList.add(new SelectionButton("Six", false));

        mPeopleButtonAdapter = new RestaurantDetailsButtonAdapter(mPeopleList);
        mPeopleRecycler.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mPeopleRecycler.setLayoutManager(llm);
        mPeopleRecycler.hasFixedSize();
        mPeopleRecycler.setAdapter(mPeopleButtonAdapter);

        mPeopleRecycler.addOnItemTouchListener(new RecyclerTouchListener(this, mPeopleRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (position > -1) {
                    for (int i = 0; i < mPeopleList.size(); i++)
                        mPeopleList.get(i).setSelected(false);
                    mPeopleList.get(position).setSelected(true);
                    mPeopleButtonAdapter.notifyDataSetChanged();
                    selectedPeople = mPeopleList.get(position).getmText();
                    isPeopleSelected = true;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    // TODO : Redesign New Function

    private void setImagesToAdapter(final RestaurantDetailsModel restaurantDetailsModel) {

        List<String> urlList = new ArrayList<>();

        for (int i = 0; i < mFeatureImageCount; i++) {

            if (restaurantDetailsModel.getResult().getFeaturedImage().get(i).getFeaturedImage() != null) {
                urlList.add(restaurantDetailsModel.getResult().getFeaturedImage().get(i).getFeaturedImage());
            }
        }

        mRestaurantDetailsImageAdapter = new RestaurantDetailsImageAdapter(urlList);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRestaurantDetailsImageRecycler.setLayoutManager(llm);
        mRestaurantDetailsImageRecycler.setItemAnimator(new DefaultItemAnimator());
        mRestaurantDetailsImageRecycler.hasFixedSize();
        mRestaurantDetailsImageRecycler.setAdapter(mRestaurantDetailsImageAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

    }

    @Override
    public void doTaskAfterPermission() {
//        gps = new GPSTracker(RestaurantsDetailsActivity.this);
//
//
//        LocationManager manager = (LocationManager) RestaurantsDetailsActivity.this.getSystemService(Context.LOCATION_SERVICE);
//        boolean statusOfGPS;
//        if (manager != null) {
//            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (!statusOfGPS) {
//                showSettingsAlert();
//            }
//        }
//
//        // check if GPS enabled
//        if (gps.canGetLocation()) {
//
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//
//            //TODO : Setting origin location
//
//            origin = new LatLng(latitude, longitude);
//            if (latitude == 0.0 && longitude == 0.0) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        doTaskAfterPermission();
//                    }
//                }, 3000);
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case AppConstants.MULTIPLE_PERMISSIONS: {
                boolean gotAllPermissions = true;
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            gotAllPermissions = false;
                        }
                    }
                } else {
                    gotAllPermissions = false;
                }
                if (gotAllPermissions) {
                    doTaskAfterPermission();
                }  /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SplashScreenActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setMessage("Storage permission is required. Please allow this permission");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            checkPermissions();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                    alertBuilder.setCancelable(false);*/
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                doTaskAfterPermission();
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                RestaurantsDetailsActivity.this.finish();
            }
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {

        RestaurantsDetailsActivity.this.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}