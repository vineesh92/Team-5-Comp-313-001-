package com.restaurant.manasa.restauranthub.ui.forgot_password;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.ForgotPassWordOtpModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantForgotPassWordContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RestaurantForgotPassWordInteractor implements RestaurantForgotPassWordContract.Interactor {
    @Override
    public void requestOtp(final OnFinishedListener onFinishedListener,  String eMail) {
        if (onFinishedListener != null) {

            Call<ForgotPassWordOtpModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.getForgotPassWord(eMail);

            call.enqueue(new Callback<ForgotPassWordOtpModel>() {

                @Override
                public void onResponse(@NonNull Call<ForgotPassWordOtpModel> call,
                                       @NonNull Response<ForgotPassWordOtpModel> response) {

                    ForgotPassWordOtpModel forgotPassWordOtpModel = response.body();
                    if (forgotPassWordOtpModel != null) {
                        onFinishedListener.onFinished(forgotPassWordOtpModel);
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