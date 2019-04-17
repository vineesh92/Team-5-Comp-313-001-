package com.restaurant.manasa.restauranthub.ui.my_bookings;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.models.MyBookingsResponseModel;
import com.restaurant.manasa.restauranthub.mvp.MyBookingsContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBookingsInteractor implements MyBookingsContract.Interactor {

    @Override
    public void requestMyBookings(final MyBookingsContract.Interactor.OnFinishedListener onFinishedListener) {

        if (onFinishedListener != null) {
            Call<MyBookingsResponseModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.getMyBookings();

            call.enqueue(new Callback<MyBookingsResponseModel>() {


                @Override
                public void onResponse(@NonNull Call<MyBookingsResponseModel> call,
                                       @NonNull Response<MyBookingsResponseModel> response) {

                    MyBookingsResponseModel restaurantModelList = response.body();
                    if (restaurantModelList != null) {

                        List<MyBookingsItem> myBookingsItems = restaurantModelList.getResult();

                        onFinishedListener.onFinished(myBookingsItems);
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