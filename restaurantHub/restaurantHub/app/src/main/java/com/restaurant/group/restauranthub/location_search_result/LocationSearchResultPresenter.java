package com.restaurant.manasa.restauranthub.ui.location_search_result;

import com.restaurant.manasa.restauranthub.models.RestaurantModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantListingContract;


public class LocationSearchResultPresenter implements RestaurantListingContract.Presenter, RestaurantListingContract.Interactor.OnFinishedListener {

    private RestaurantListingContract.View mainView;
    private final RestaurantListingContract.Interactor restaurantInteractor;
    private String mToken;

    LocationSearchResultPresenter(RestaurantListingContract.Interactor getNoticeInteractor, RestaurantListingContract.View mainView) {
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
        restaurantInteractor.getRestaurantList(this, mToken);

    }

    @Override
    public void requestRestaurantListData(String token) {

        mToken = token;

        mainView.showProgress();
        restaurantInteractor.getRestaurantList(this, token);
    }

    @Override
    public void onFinished(RestaurantModel restaurantModels) {

        if (mainView != null) {
            mainView.onLoadRestaurantList(restaurantModels);
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
