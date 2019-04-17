package com.restaurant.manasa.restauranthub.ui.login;

import com.restaurant.manasa.restauranthub.models.LoginModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantLoginContract;

import java.util.regex.Pattern;


class RestaurantLoginPresenter implements RestaurantLoginContract.Presenter, RestaurantLoginContract.Interactor.OnFinishedListener {

    private RestaurantLoginContract.View loginMainView;
    private RestaurantLoginContract.Interactor restaurantLoginInteractor;
    private String mEmail;
    private String mPassword;

    RestaurantLoginPresenter(RestaurantLoginContract.Interactor restaurantLoginInteractor, RestaurantLoginContract.View loginMainView) {
        this.loginMainView = loginMainView;
        this.restaurantLoginInteractor = restaurantLoginInteractor;
    }

    @Override
    public void onDestroy() {

        loginMainView = null;
    }

    @Override
    public void requestLoginData() {
        restaurantLoginInteractor.requestLogin(this, mEmail, mPassword);
    }


    @Override
    public void onFinished(LoginModel loginModel) {
        if (loginMainView != null) {
            loginMainView.onLoadLoginData(loginModel);
            loginMainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (loginMainView != null) {
            loginMainView.onResponseFailure(t);
            loginMainView.hideProgress();
        }
    }

    void requestLogin(String mEmail, String mPassword) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        if (mEmail != null && !mEmail.equals("")) {
            if (isValidEmailId(mEmail)) {
                if (mPassword != null && !mPassword.equals("")) {
                    loginMainView.showProgress();
                    requestLoginData();
                } else {
                    loginMainView.validationError("Password Field");
                }
            } else {
                loginMainView.validationError("Email");
            }
        } else {
            loginMainView.validationError("Email Field");
        }
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}