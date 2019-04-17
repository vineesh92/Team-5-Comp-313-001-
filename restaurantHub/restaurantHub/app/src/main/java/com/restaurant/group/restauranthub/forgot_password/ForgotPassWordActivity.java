package com.restaurant.manasa.restauranthub.ui.forgot_password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.ForgotPassWordOtpModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantForgotPassWordContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.otp.OtpActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;


public class ForgotPassWordActivity extends BaseActivity implements RestaurantForgotPassWordContract.View {
    private EditText edtEmail;
    private ProgressDialog mProgressDialog;
    private RestaurantForgotPassWordPresenter restaurantForgotPassWordPresenter;
    private TextInputLayout emailTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUi();

        restaurantForgotPassWordPresenter = new RestaurantForgotPassWordPresenter(new RestaurantForgotPassWordInteractor(),
                ForgotPassWordActivity.this);

        mProgressDialog = new ProgressDialog(ForgotPassWordActivity.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

    }

    private void initUi() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        edtEmail = findViewById(R.id.edtEmail);
        TextView btnSendOtpView = findViewById(R.id.btnSendOtpView);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);

        btnSendOtpView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (Utils.isNetworkAvailable(ForgotPassWordActivity.this)) {

                    String mEmail = edtEmail.getText().toString().trim();
                    restaurantForgotPassWordPresenter.requestOtp(mEmail);
                } else {
                    showSnackBar();
                }
            }
        });
        toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    
    @Override
    public void showProgress() {
        emailTextInputLayout.setError(null);
        Utils.showProgressDialog(mProgressDialog, "", getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        Utils.hideProgressDialog(mProgressDialog);
    }

    @Override
    public void validationError(String msg) {
        emailTextInputLayout.setError(msg);
        emailTextInputLayout.requestFocus();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onOtpResponse(ForgotPassWordOtpModel forgotPassWordOtpModel) {
        if (forgotPassWordOtpModel != null && forgotPassWordOtpModel.getStatus() != null) {

            if (forgotPassWordOtpModel.getStatus().equals("success")) {
                Intent intentRestaurantList = new Intent(ForgotPassWordActivity.this,
                        OtpActivity.class);
                startActivity(intentRestaurantList);
                overridePendingTransition(R.anim.right, R.anim.left);
                ForgotPassWordActivity.this.finish();
            }

        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
