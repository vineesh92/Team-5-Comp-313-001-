package com.restaurant.manasa.restauranthub.ui.login;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.LoginModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantLoginContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RestaurantLoginInteractor implements RestaurantLoginContract.Interactor {

    @Override
    public void requestLogin(final OnFinishedListener onFinishedListener, String eMail, String passWord) {
        if (onFinishedListener != null) {

            Call<LoginModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.login(eMail, passWord);

            call.enqueue(new Callback<LoginModel>() {

                @Override
                public void onResponse(@NonNull Call<LoginModel> call,
                                       @NonNull Response<LoginModel> response) {

                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        onFinishedListener.onFinished(loginModel);
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