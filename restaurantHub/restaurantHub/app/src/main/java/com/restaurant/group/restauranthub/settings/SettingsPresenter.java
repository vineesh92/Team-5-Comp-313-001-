package com.restaurant.manasa.restauranthub.ui.settings;

import com.restaurant.manasa.restauranthub.models.MyAccountModel;
import com.restaurant.manasa.restauranthub.models.SuccessModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantMyAccountContract;
import com.restaurant.manasa.restauranthub.mvp.SettingsContract;


public class SettingsPresenter implements SettingsContract.Presenter, RestaurantMyAccountContract.Interactor.OnFinishedListener {

    private final RestaurantMyAccountContract.Interactor interactor;
    private SettingsContract.View view;

    SettingsPresenter(RestaurantMyAccountContract.Interactor interactor, SettingsContract.View view) {

        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void requestAccountDetails(String userId) {

        view.showProgress();
        interactor.sendMyAccount(this, userId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFinished(MyAccountModel myAccountModel) {

        view.loadMyAccountResponse(myAccountModel);
        view.hideProgress();
    }

    @Override
    public void onUpdateMyAccountFinished(SuccessModel successModel) {

    }

    @Override
    public void onUpdateMyAccountImageFinished() {

    }

    @Override
    public void onFailure(Throwable t) {
        view = null;
    }
}