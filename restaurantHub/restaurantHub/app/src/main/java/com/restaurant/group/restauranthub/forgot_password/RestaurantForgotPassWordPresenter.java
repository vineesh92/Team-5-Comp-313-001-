package com.restaurant.manasa.restauranthub.ui.forgot_password;

import com.restaurant.manasa.restauranthub.models.ForgotPassWordOtpModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantForgotPassWordContract;

import java.util.regex.Pattern;


public class RestaurantForgotPassWordPresenter implements RestaurantForgotPassWordContract.Presenter, RestaurantForgotPassWordContract.Interactor.OnFinishedListener {

    private RestaurantForgotPassWordContract.View forgotPassWordOtpView;
    private RestaurantForgotPassWordContract.Interactor restaurantInteractor;

    private String mEmail;

    RestaurantForgotPassWordPresenter(RestaurantForgotPassWordContract.Interactor getNoticeInteractor, RestaurantForgotPassWordContract.View mainView) {
        this.forgotPassWordOtpView = mainView;
        this.restaurantInteractor = getNoticeInteractor;
    }

    @Override
    public void onDestroy() {

        forgotPassWordOtpView = null;
    }

    @Override
    public void requestOtpResponse() {
        restaurantInteractor.requestOtp(this, mEmail);
    }

    @Override
    public void onFinished(ForgotPassWordOtpModel forgotPassWordOtpModel) {
        if (forgotPassWordOtpView != null) {
            forgotPassWordOtpView.onOtpResponse(forgotPassWordOtpModel);
            forgotPassWordOtpView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (forgotPassWordOtpView != null) {
            forgotPassWordOtpView.onResponseFailure(t);
            forgotPassWordOtpView.hideProgress();
        }
    }

    void requestOtp( String mEmail) {
        this.mEmail = mEmail;
        if (mEmail != null && !mEmail.equals("")) {
            if (isValidEmailId(mEmail)) {
                forgotPassWordOtpView.showProgress();
                requestOtpResponse();
            } else {
                forgotPassWordOtpView.validationError("Not valid email address");
            }
        } else {
            forgotPassWordOtpView.validationError("Field cannot be empty");
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