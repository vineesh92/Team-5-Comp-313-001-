package com.restaurant.manasa.restauranthub.ui.change_email;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.mvp.ChangeEmailContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;

public class ChangeEmailActivity extends BaseActivity implements ChangeEmailContract.View {

    private EditText mOldEmailEditText;
    private EditText mNewEmailEditText;
    private TextInputLayout mOldEmailLayout;
    private TextInputLayout mNewEmailLayout;
    private Button mUpdateButton;
    private ChangeEmailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        mPresenter = new ChangeEmailPresenter(this, new ChangeEmailInteractor());

        initUi();
    }

    private void initUi() {

        mOldEmailEditText = findViewById(R.id.edtemailView);
        mNewEmailEditText = findViewById(R.id.edtConfirmEmailView);

        mOldEmailLayout = findViewById(R.id.layout_oldEmail);
        mNewEmailLayout = findViewById(R.id.layout_newEmail);

        mUpdateButton = findViewById(R.id.btnResetView);

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.requestEmailChange(mOldEmailEditText.getText().toString().trim(), mNewEmailEditText.getText().toString().trim());
            }
        });

        Toolbar toolBar = findViewById(R.id.toolBar);
        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void validationError(String msg) {

        switch (msg) {

            case "old email":

                mOldEmailLayout.setError("Field cannot be empty");
                break;

            case "new email":

                mOldEmailLayout.setError(null);
                mNewEmailLayout.setError("Field cannot be empty");
                break;

            case "old email format":

                mNewEmailLayout.setError(null);
                mOldEmailLayout.setError("Not valid email address");
                break;

            case "new email format":

                mOldEmailLayout.setError(null);
                mNewEmailLayout.setError("Not valid email addressd");
                break;
        }

    }

    @Override
    public void onSuccess() {


        Utils.hideKeyboard(this);
        onBackPressed();
        Toast.makeText(this, "email updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(this);
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
