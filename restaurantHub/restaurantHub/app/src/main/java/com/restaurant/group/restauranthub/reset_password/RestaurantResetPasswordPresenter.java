package com.restaurant.manasa.restauranthub.ui.reset_password;

import com.restaurant.manasa.restauranthub.models.ResetPasswordModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantResetPasswordContract;


public class RestaurantResetPasswordPresenter implements RestaurantResetPasswordContract.Presenter, RestaurantResetPasswordContract.Interactor.OnFinishedListener {

    private RestaurantResetPasswordContract.View resetPasswordMainView;
    private final RestaurantResetPasswordContract.Interactor restaurantResetPasswordInteractor;
    private String mToken;
    private String mPassword;

    RestaurantResetPasswordPresenter(RestaurantResetPasswordContract.Interactor restaurentResetPasswordIntractor, RestaurantResetPasswordContract.View ResetPasswordmainView) {
        this.resetPasswordMainView = ResetPasswordmainView;
        this.restaurantResetPasswordInteractor = restaurentResetPasswordIntractor;
    }

    @Override
    public void onDestroy() {

        resetPasswordMainView = null;
    }

    @Override
    public void requestOtpResponse() {
        restaurantResetPasswordInteractor.resetPassWord(this, mToken,mPassword);
    }


    @Override
    public void onFinished(ResetPasswordModel passwordModel) {
        if (resetPasswordMainView != null) {
            resetPasswordMainView.onResetPasswordResponse(passwordModel);
            resetPasswordMainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (resetPasswordMainView != null) {
            resetPasswordMainView.onResponseFailure(t);
            resetPasswordMainView.hideProgress();
        }
    }

    void requestResetPasssWord(String mToken, String mPassword, String mConfirmPassword) {
        this.mToken = mToken;
        this.mPassword = mPassword;
        if (mPassword != null && !mPassword.equals("")) {
            if (mConfirmPassword != null && !mConfirmPassword.equals("")) {
                if (mPassword.equals(mConfirmPassword)) {
                    resetPasswordMainView.showProgress();
                    requestOtpResponse();
                } else {
                    resetPasswordMainView.validationError("Password does not match");
                }
            } else {
                resetPasswordMainView.validationError("Confirm Password");
            }
        } else {
            resetPasswordMainView.validationError("Password");
        }
    }
}