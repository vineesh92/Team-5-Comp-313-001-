package com.restaurant.manasa.restauranthub.ui.change_email;

import com.restaurant.manasa.restauranthub.mvp.ChangeEmailContract;


public class ChangeEmailInteractor implements ChangeEmailContract.Interactor {
    @Override
    public void changeEmail(String oldEmail, String newEmail, OnUpdationCompleteListener listener) {

        listener.onFinish();
    }
}
