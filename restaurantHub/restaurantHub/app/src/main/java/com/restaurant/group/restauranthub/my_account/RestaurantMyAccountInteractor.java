package com.restaurant.manasa.restauranthub.ui.my_account;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.restaurant.manasa.restauranthub.models.MyAccountModel;
import com.restaurant.manasa.restauranthub.models.SuccessModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantMyAccountContract;
import com.restaurant.manasa.restauranthub.webservices.APIClient;
import com.restaurant.manasa.restauranthub.webservices.APIInterface;

import java.io.File;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantMyAccountInteractor implements RestaurantMyAccountContract.Interactor {
    @Override
    public void sendMyAccount(final OnFinishedListener onFinishedListener, String token) {
        if (onFinishedListener != null) {

            Call<MyAccountModel> call;
            APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
            call = apiInterface.myAccount(token);


            call.enqueue(new Callback<MyAccountModel>() {
                @Override
                public void onResponse(@NonNull Call<MyAccountModel> call,
                                       @NonNull Response<MyAccountModel> response) {

                    MyAccountModel loginModel = response.body();
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

    @Override
    public void updateMyAccountResponse(final OnFinishedListener onFinishedListener, String mToken,
                                        String mName, String mPhoneNumber, String mEmail) {

        Call<SuccessModel> call;
        APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);
        call = apiInterface.updateMyProfile(mToken, mName, mPhoneNumber, mEmail);


        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(@NonNull Call<SuccessModel> call,
                                   @NonNull Response<SuccessModel> response) {

                SuccessModel successModel = response.body();
                if (successModel != null) {
                    onFinishedListener.onUpdateMyAccountFinished(successModel);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void updateMyAccountImage(Uri uri, final OnFinishedListener onFinishedListener) {

        APIInterface apiInterface = APIClient.restaurantApp().create(APIInterface.class);

        File file = new File(uri.getPath());

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<Result> resultCall = apiInterface.uploadImage(body);

        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {


                // Response Success or Fail
                if (response.isSuccessful()) {
                    onFinishedListener.onUpdateMyAccountImageFinished();
                } else {
                    onFinishedListener.onUpdateMyAccountImageFinished();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}