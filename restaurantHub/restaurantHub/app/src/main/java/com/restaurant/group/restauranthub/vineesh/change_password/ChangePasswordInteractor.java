package com.restaurant.manasa.restauranthub.ui.change_password;

import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.ResetPasswordModel;
import com.restaurant.manasa.restauranthub.mvp.ChangePasswordContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordInteractor implements ChangePasswordContract.Interactor {
    @Override
    public void resetPassWord(final ChangePasswordContract.Interactor.OnFinishedListener onFinishedListener, String mToken, String mPassword) {
        if (onFinishedListener != null) {
            Call<ResetPasswordModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.resetPassword(mToken, mPassword);


            call.enqueue(new Callback<ResetPasswordModel>() {

                @Override
                public void onResponse(@NonNull Call<ResetPasswordModel> call,
                                       @NonNull Response<ResetPasswordModel> response) {

                    ResetPasswordModel ResetPasswordModelList = response.body();
                    if (ResetPasswordModelList != null) {
                        onFinishedListener.onFinished(ResetPasswordModelList);
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
