package com.restaurant.manasa.restauranthub.ui.my_bookings;

import com.restaurant.manasa.restauranthub.models.MyBookingsItem;
import com.restaurant.manasa.restauranthub.mvp.MyBookingsContract;

import java.util.List;

public class MyBookingsPresenter implements MyBookingsContract.Presenter, MyBookingsContract.Interactor.OnFinishedListener {

    private final MyBookingsContract.Interactor interactor;
    private MyBookingsContract.View view;

    MyBookingsPresenter(MyBookingsContract.Interactor interactor, MyBookingsContract.View view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onDestroy() {

        view = null;
    }

    @Override
    public void requestMyBookings() {
        if (view != null)
            view.showProgress();
        interactor.requestMyBookings(this);
    }

    @Override
    public void onFinished(List<MyBookingsItem> myBookingsItems) {
        if (view != null) {
            view.hideProgress();
            view.onMyBookingsResponse(myBookingsItems);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        view.hideProgress();
    }
}