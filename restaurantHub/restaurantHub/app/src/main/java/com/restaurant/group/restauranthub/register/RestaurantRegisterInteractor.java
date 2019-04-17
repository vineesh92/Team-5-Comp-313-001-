package com.restaurant.manasa.restauranthub.ui.register;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.LoginModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantRegisterContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RestaurantRegisterInteractor implements RestaurantRegisterContract.Interactor {
    @Override
    public void requestRegister(final OnFinishedListener onFinishedListener, String eMail,
                                String passWord, String mName, String mPhoneNumber) {
        if (onFinishedListener != null) {

            Call<LoginModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.register(eMail, passWord, mName, mPhoneNumber);


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