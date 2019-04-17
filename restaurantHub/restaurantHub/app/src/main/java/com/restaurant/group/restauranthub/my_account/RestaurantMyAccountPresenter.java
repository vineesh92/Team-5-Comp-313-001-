package com.restaurant.manasa.restauranthub.ui.my_account;

import android.net.Uri;

import com.restaurant.manasa.restauranthub.models.MyAccountModel;
import com.restaurant.manasa.restauranthub.models.SuccessModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantMyAccountContract;



public class RestaurantMyAccountPresenter implements RestaurantMyAccountContract.Presenter, RestaurantMyAccountContract.Interactor.OnFinishedListener {

    private RestaurantMyAccountContract.View myAccountView;
    private final RestaurantMyAccountContract.Interactor restaurantLoginInteractor;
    private String token;

    private String mToken;
    private String mName;
    private String mPhoneNumber;
    private String mEmail;


    RestaurantMyAccountPresenter(RestaurantMyAccountContract.Interactor restaurantLoginInteractor, RestaurantMyAccountContract.View loginMainView) {

        this.myAccountView = loginMainView;
        this.restaurantLoginInteractor = restaurantLoginInteractor;
    }

    @Override
    public void onDestroy() {

        myAccountView = null;
    }

    @Override
    public void requestMyAccountResponse() {
        myAccountView.showProgress();
        restaurantLoginInteractor.sendMyAccount(this, token);
    }

    @Override
    public void updateMyAccountResponse() {
        myAccountView.showProgressDialog();
        restaurantLoginInteractor.updateMyAccountResponse(this,mToken,mName, mPhoneNumber, mEmail);
    }

    @Override
    public void updateMyAccountImage(Uri uri) {
        myAccountView.showProgressDialog();
        restaurantLoginInteractor.updateMyAccountImage(uri, this);
    }

    @Override
    public void onFinished(MyAccountModel myAccountModel) {
        if (myAccountView != null) {
            myAccountView.onMyAccountResponse(myAccountModel);
            myAccountView.hideProgress();
        }
    }

    @Override
    public void onUpdateMyAccountFinished(SuccessModel successModel) {
        if (myAccountView != null) {
            myAccountView.onUpdateMyAccountResponse(successModel);
            myAccountView.hideProgressDialog();
        }
    }

    @Override
    public void onUpdateMyAccountImageFinished() {
        if (myAccountView != null) {
            myAccountView.hideProgressDialog();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (myAccountView != null) {
            myAccountView.onResponseFailure(t);
            myAccountView.hideProgress();
            myAccountView.hideProgressDialog();
        }
    }

    void requestMyAccount(String token) {
        this.token = token;
        requestMyAccountResponse();
    }

    void updateMyAccount(String mToken, String mName, String mPhoneNumber, String mEmail) {
        this.mToken = mToken;
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        updateMyAccountResponse();
    }
}