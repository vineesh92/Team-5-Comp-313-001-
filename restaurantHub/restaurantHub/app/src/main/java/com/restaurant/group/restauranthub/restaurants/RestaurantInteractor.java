package com.restaurant.manasa.restauranthub.ui.restaurants;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.RestaurantModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantListingContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantInteractor implements RestaurantListingContract.Interactor {


    @Override
    public void getRestaurantList(final OnFinishedListener onFinishedListener, String token) {
        if (onFinishedListener != null) {
            Call<RestaurantModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.getRestaurant(token);

            call.enqueue(new Callback<RestaurantModel>() {

                @Override
                public void onResponse(@NonNull Call<RestaurantModel> call,
                                       @NonNull Response<RestaurantModel> response) {

                    RestaurantModel restaurantModelList = response.body();
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
