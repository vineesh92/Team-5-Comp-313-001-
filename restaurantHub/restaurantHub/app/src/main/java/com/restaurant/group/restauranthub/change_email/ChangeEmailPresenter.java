package com.restaurant.manasa.restauranthub.ui.change_email;

import com.restaurant.manasa.restauranthub.mvp.ChangeEmailContract;

import java.util.regex.Pattern;

public class ChangeEmailPresenter implements ChangeEmailContract.Presenter, ChangeEmailContract.Interactor.OnUpdationCompleteListener {

    private ChangeEmailContract.View view;
    private ChangeEmailInteractor interactor;

    ChangeEmailPresenter(ChangeEmailContract.View view, ChangeEmailInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void requestEmailChange(String oldEmail, String newEmail) {


        if (oldEmail.isEmpty()) {

            view.validationError("old email");

        } else if (newEmail.isEmpty()) {

            view.validationError("new email");

        } else if (!isValidEmaillId(oldEmail)) {

            view.validationError("old email format");

        } else if (!isValidEmaillId(newEmail)) {

            view.validationError("new email format");

        } else {

            interactor.changeEmail(oldEmail, newEmail, this);
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

    @Override
    public void onFinish() {

        view.onSuccess();
    }

    @Override
    public void onFailure() {

    }
}
