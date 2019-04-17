package com.restaurant.manasa.restauranthub.ui.my_bookings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.mvp.MyBookingsContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.settings.SettingsActivity;
import com.restaurant.manasa.restauranthub.utils.BottomNavigationViewBehaviour;
import com.restaurant.manasa.restauranthub.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends BaseActivity implements MyBookingsContract.View {

    private RecyclerView mMyBookingsRecycler;
    private MyBookingsAdapter mMyBookingsAdapter;
    private final ArrayList<MyBookingsItem> mMyBookingsList = new ArrayList<>();
    private MyBookingsPresenter mMyBookingsPresenter;
    private ProgressBar mProgressBar;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        mMyBookingsPresenter = new MyBookingsPresenter(new MyBookingsInteractor(), MyBookingsActivity.this);

        initUI();

        mMyBookingsPresenter.requestMyBookings();
    }

    private void initUI() {

        mMyBookingsRecycler = findViewById(R.id.recycler_mybookings);
        mMyBookingsRecycler.setLayoutManager(new LinearLayoutManager(MyBookingsActivity.this, LinearLayoutManager.VERTICAL, false));
        mMyBookingsRecycler.setItemAnimator(new DefaultItemAnimator());
        mMyBookingsAdapter = new MyBookingsAdapter(MyBookingsActivity.this, mMyBookingsList);
        mMyBookingsRecycler.setAdapter(mMyBookingsAdapter);
        mProgressBar = findViewById(R.id.progressBar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehaviour());

        mBottomNavigationView.setSelectedItemId(R.id.navigation_bookings);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;

                switch (item.getItemId()) {

                    case R.id.navigation_home:

                        onBackPressed();
                        break;

                    case R.id.navigation_account:

                        if (Utils.isNetworkAvailable(MyBookingsActivity.this)) {
                            intent = new Intent(MyBookingsActivity.this, SettingsActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right, R.anim.left);
                            finish();
                        } else showSnackBar();
                        break;
                }
                return true;
            }
        });


        android.support.v7.widget.Toolbar toolBar = findViewById(R.id.toolBar);
        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyBookingsPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void showProgress() {

        mMyBookingsRecycler.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        mMyBookingsRecycler.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMyBookingsResponse(List<MyBookingsItem> myBookingsItems) {

        this.mMyBookingsList.clear();
        this.mMyBookingsList.addAll(myBookingsItems);
        mMyBookingsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {

        super.onResume();
        mBottomNavigationView.setSelectedItemId(R.id.navigation_bookings);
    }
}