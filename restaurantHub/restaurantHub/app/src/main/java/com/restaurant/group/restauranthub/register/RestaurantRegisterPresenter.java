package com.restaurant.manasa.restauranthub.ui.register;

import com.restaurant.manasa.restauranthub.models.LoginModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantRegisterContract;

import java.util.regex.Pattern;



class RestaurantRegisterPresenter implements RestaurantRegisterContract.Presenter, RestaurantRegisterContract.Interactor.OnFinishedListener {

    private RestaurantRegisterContract.View loginMainView;
    private RestaurantRegisterContract.Interactor restaurentLoginIntractor;
    private String mName;
    private String mPhoneNumber;
    private String mEmail;
    private String mPassword;

    RestaurantRegisterPresenter(RestaurantRegisterContract.Interactor restaurentLoginIntractor, RestaurantRegisterContract.View loginmainView) {
        this.loginMainView = loginmainView;
        this.restaurentLoginIntractor = restaurentLoginIntractor;
    }

    @Override
    public void onDestroy() {

        loginMainView = null;
    }

    @Override
    public void requestRegisterData() {
        restaurentLoginIntractor.requestRegister(this, mEmail, mPassword,mName,mPhoneNumber);
    }


    @Override
    public void onFinished(LoginModel loginModel) {
        if (loginMainView != null) {
            loginMainView.onLoadRegisterData(loginModel);
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

    void requestRegister(String mName, String mPhoneNumber, String mEmail, String mPassword) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        this.mPassword = mPassword;

        if (mName != null && !mName.equals("")) {
            if (mPhoneNumber != null && !mPhoneNumber.equals("")) {

                if (isValidMobile(mPhoneNumber)) {
                    if (mEmail != null && !mEmail.equals("")) {

                        if (isValidEmaillId(mEmail)) {
                            if (mPassword != null && !mPassword.equals("")) {
                                loginMainView.showProgress();
                                requestRegisterData();
                            } else {
                                loginMainView.validationError("Password empty");
                            }
                        }else {
                            loginMainView.validationError("Email");
                        }

                    } else {
                        loginMainView.validationError("Email empty");
                    }
                } else {
                    loginMainView.validationError("Mobile Number");
                }

            } else {
                loginMainView.validationError("Mobile Number empty");
            }
        } else {
            loginMainView.validationError("Full Name empty");
        }


    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() >= 6 && phone.length() <= 13;
        } else {
            check = false;
        }
        return check;
    }

}