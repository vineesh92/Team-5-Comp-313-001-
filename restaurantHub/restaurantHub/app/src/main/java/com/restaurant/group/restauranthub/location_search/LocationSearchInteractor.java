package com.restaurant.manasa.restauranthub.ui.location_search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.restaurant.manasa.restauranthub.models.PlaceAPISearchResultModel;
import com.restaurant.manasa.restauranthub.mvp.LocationSearchContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class LocationSearchInteractor implements LocationSearchContract.Interactor, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private final AutocompleteFilter filter = new AutocompleteFilter.Builder().setCountry("IN").build();
    private final Context mContext;
    private static final int GOOGLE_API_CLIENT_ID = 1;

    LocationSearchInteractor(Context context) {
        this.mContext = context;
    }

    @Override
    public ArrayList<PlaceAPISearchResultModel> getPredictions(CharSequence constraint) {

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage((LocationSearchActivity) mContext, GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        PendingResult<AutocompletePredictionBuffer> results =
                Places.GeoDataApi
                        .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                                null, filter);
        // Wait for predictions, set the timeout.
        AutocompletePredictionBuffer autocompletePredictions = results
                .await(60, TimeUnit.SECONDS);
        final Status status = autocompletePredictions.getStatus();
        if (!status.isSuccess()) {
            autocompletePredictions.release();
            return null;
        }

        Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
        ArrayList<PlaceAPISearchResultModel> resultList = new ArrayList<>(autocompletePredictions.getCount());
        while (iterator.hasNext()) {

            AutocompletePrediction prediction = iterator.next();

            resultList.add(new PlaceAPISearchResultModel(prediction.getPlaceId(),
                    prediction.getPrimaryText(null), prediction.getSecondaryText(null)));

        }
        // Buffer release
        autocompletePredictions.release();
        return resultList;
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