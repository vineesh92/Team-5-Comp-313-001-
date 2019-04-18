package com.restaurant.manasa.restauranthub.ui.location_search;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.restaurant.manasa.restauranthub.models.PlaceAPISearchResultModel;
import com.restaurant.manasa.restauranthub.mvp.LocationSearchContract;

import java.util.ArrayList;


public class LocationSearchPresenter implements LocationSearchContract.Presenter {

    private static final int AUTOCOMPLETE_THRESHOLD = 2;
    private LocationSearchContract.View mLocationSearchView;
    private final LocationSearchContract.Interactor mLocationSearchInteractor;
    private final ArrayList<PlaceAPISearchResultModel> mListPlace = new ArrayList<>();

    LocationSearchPresenter(LocationSearchContract.Interactor interactor, LocationSearchContract.View view) {
        mLocationSearchView = view;
        mLocationSearchInteractor = interactor;
    }

    @Override
    public void onDestroy() {

        mLocationSearchView = null;
    }

    @Override
    public void onTextChanged(CharSequence s) {
        String input = s.toString().trim();
        if (input.length() >= AUTOCOMPLETE_THRESHOLD) {

            new PlaceSuggestionOperation().execute(input);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class PlaceSuggestionOperation extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            mListPlace.clear();

            ArrayList<PlaceAPISearchResultModel> test = mLocationSearchInteractor.getPredictions(params[0]);

            if (test != null) {
                mListPlace.addAll(test);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            mLocationSearchView.onLoadLocationSearchResult(mListPlace);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}