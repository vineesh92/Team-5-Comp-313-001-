package com.restaurant.manasa.restauranthub.ui.restaurants_details;

import com.restaurant.manasa.restauranthub.models.RestaurantDetailsModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantDetailsContract;

public class RestaurantDetailsPresenter implements RestaurantDetailsContract.Presenter, RestaurantDetailsContract.Interactor.OnFinishedListener {

    private RestaurantDetailsContract.View mainView;
    private final RestaurantDetailsContract.Interactor restaurantInteractor;
    private String mToken;

    RestaurantDetailsPresenter(RestaurantDetailsContract.Interactor getNoticeInteractor, RestaurantDetailsContract.View mainView) {
        this.mainView = mainView;
        this.restaurantInteractor = getNoticeInteractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;
    }

    @Override
    public void onRefreshButtonClick() {

        if (mainView != null) {
            mainView.showProgress();
        }
        restaurantInteractor.getRestaurantDetails(this, mToken);

    }

    @Override
    public void requestRestaurantDetails(String token) {
        mToken = token;
        mainView.showProgress();
        restaurantInteractor.getRestaurantDetails(this,token);
    }


    @Override
    public void onFinished(RestaurantDetailsModel restaurantModels) {
        if (mainView != null) {
            mainView.onLoadRestaurantDetails(restaurantModels);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}