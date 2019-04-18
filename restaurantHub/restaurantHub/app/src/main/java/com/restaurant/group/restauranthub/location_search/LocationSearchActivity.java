package com.restaurant.manasa.restauranthub.ui.location_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.PlaceAPISearchResultModel;
import com.restaurant.manasa.restauranthub.mvp.LocationSearchContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.location_search_result.LocationSearchResultActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;

import java.util.ArrayList;

public class LocationSearchActivity extends BaseActivity implements LocationSearchContract.View, RestaurantLocationSearchAdapter.OnLoacationSelectedListener {

    private LocationSearchPresenter mLocationSearchPresenter;
    private RestaurantLocationSearchAdapter mRestaurantLocationSearchAdapter;
    private final ArrayList<PlaceAPISearchResultModel> mList = new ArrayList<>();

    private  Boolean isBackEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);

        mLocationSearchPresenter = new LocationSearchPresenter(new LocationSearchInteractor(this), LocationSearchActivity.this);

        if(getIntent()!= null && getIntent().getExtras()!= null){
            Bundle bundle = getIntent().getExtras();
            if(bundle.containsKey("isBackEnabled")){

                isBackEnabled = bundle.getBoolean("isBackEnabled", false);

            }
        }

        initUI();
    }

    private void initUI() {

        RecyclerView mLocationListRecyclerView = findViewById(R.id.recyclerViewPlaceSuggestions);
        EditText mLocationSearchEditText = findViewById(R.id.ipEditTextSourceLocation);

        mLocationListRecyclerView.setLayoutManager(new LinearLayoutManager(LocationSearchActivity.this, LinearLayoutManager.VERTICAL, false));
        mLocationListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRestaurantLocationSearchAdapter = new RestaurantLocationSearchAdapter(this, mList);
        mRestaurantLocationSearchAdapter.setKeyWord("");
        mLocationListRecyclerView.setAdapter(mRestaurantLocationSearchAdapter);

        Toolbar toolBar = findViewById(R.id.toolBar);
        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        mLocationSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLocationSearchPresenter.onTextChanged(s);
                mRestaurantLocationSearchAdapter.setKeyWord(s.toString().trim());
                if (s.toString().length() == 0) {
                    mList.clear();
                    mRestaurantLocationSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationSearchPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onLoadLocationSearchResult(ArrayList<PlaceAPISearchResultModel> list) {
        this.mList.clear();
        this.mList.addAll(list);
        mRestaurantLocationSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationSelected(String location) {

//        Intent intent = new Intent();
//        intent.putExtra("Location", location);
//        setResult(RESULT_OK, intent);
//        onBackPressed();

        Utils.hideKeyboard(this);

        if (isBackEnabled){

            Intent intent = new Intent();
            intent.putExtra("Location", location);
            setResult(Activity.RESULT_OK, intent);

            LocationSearchActivity.this.finish();
            overridePendingTransition(R.anim.right, R.anim.left);

        }else {
            Intent intent1 = new Intent();
            intent1.putExtra("exit", "exit");
            setResult(Activity.RESULT_OK, intent1);
            Intent intent = new Intent(this, LocationSearchResultActivity.class);
            intent.putExtra("Location", location);
            startActivity(intent);
            overridePendingTransition(R.anim.right, R.anim.left);
            LocationSearchActivity.this.finish();
        }
    }
}