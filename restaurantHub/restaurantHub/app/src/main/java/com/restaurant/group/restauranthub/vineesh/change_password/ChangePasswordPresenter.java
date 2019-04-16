package com.restaurant.manasa.restauranthub.ui.change_password;

import com.restaurant.manasa.restauranthub.models.ResetPasswordModel;
import com.restaurant.manasa.restauranthub.mvp.ChangePasswordContract;


public class ChangePasswordPresenter implements ChangePasswordContract.Presenter, ChangePasswordContract.Interactor.OnFinishedListener {

    private ChangePasswordContract.View resetPasswordMainView;
    private final ChangePasswordContract.Interactor restaurantResetPasswordInteractor;
    private String mToken;
    private String mPassword;

    ChangePasswordPresenter(ChangePasswordContract.Interactor restaurentResetPasswordIntractor, ChangePasswordContract.View ResetPasswordmainView) {
        this.resetPasswordMainView = ResetPasswordmainView;
        this.restaurantResetPasswordInteractor = restaurentResetPasswordIntractor;
    }

    @Override
    public void onDestroy() {

        resetPasswordMainView = null;
    }

    @Override
    public void requestOtpResponse() {
        restaurantResetPasswordInteractor.resetPassWord(this, mToken, mPassword);
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

    void requestResetPasssWord(String mToken, String mOldPassword, String mPassword, String mConfirmPassword) {
        this.mToken = mToken;
        this.mPassword = mPassword;
        if (mOldPassword != null && !mOldPassword.equals("")) {
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
        } else {
            resetPasswordMainView.validationError("Old Password");
        }
    }
}
