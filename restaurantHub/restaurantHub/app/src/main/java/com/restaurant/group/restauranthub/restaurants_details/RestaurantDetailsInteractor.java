package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.RestaurantDetailsModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantDetailsContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantDetailsInteractor implements RestaurantDetailsContract.Interactor {
    @Override
    public void getRestaurantDetails(final OnFinishedListener onFinishedListener, String token) {


        if (onFinishedListener != null) {

            Call<RestaurantDetailsModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.getRestaurantDetails(token);


            call.enqueue(new Callback<RestaurantDetailsModel>() {
                @Override
                public void onResponse(@NonNull Call<RestaurantDetailsModel> call,
                                       @NonNull Response<RestaurantDetailsModel> response) {

                    RestaurantDetailsModel restaurantModelList = response.body();
                    if (restaurantModelList != null) {
                        onFinishedListener.onFinished(restaurantModelList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }
}