package com.restaurant.manasa.restauranthub.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.LoginModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantLoginContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.forgot_password.ForgotPassWordActivity;
import com.restaurant.manasa.restauranthub.ui.register.RegisterActivity;
import com.restaurant.manasa.restauranthub.ui.restaurants.MainRestaurantListingActivity;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;



public class LoginActivity extends BaseActivity implements RestaurantLoginContract.View {
    private EditText edtTextEmailSignInView;
    private EditText edtTextPasswordSignInView;
    private RestaurantLoginPresenter mRestaurantLoginPresenter;
    private ProgressDialog mProgressDialog;
    private TextInputLayout passWordTextInputLayout;
    private TextInputLayout emailTextInputLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inItUi();

        mRestaurantLoginPresenter = new RestaurantLoginPresenter(new RestaurantLoginInteractor(),
                LoginActivity.this);

        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    private void inItUi() {

        edtTextEmailSignInView = findViewById(R.id.edtTextEmailSignInView);
        edtTextPasswordSignInView = findViewById(R.id.edtTextPasswordSignInView);
        TextView textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        TextView btnLoginView = findViewById(R.id.btnLoginView);
        TextView textViewSignUpPassword = findViewById(R.id.textViewSignUpPassword);
        passWordTextInputLayout = findViewById(R.id.passWordTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);

        btnLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(LoginActivity.this)) {

                    String mEmail = edtTextEmailSignInView.getText().toString().trim();
                    String mPassword = edtTextPasswordSignInView.getText().toString().trim();

                    mRestaurantLoginPresenter.requestLogin(mEmail, mPassword);
                } else {
                    showSnackBar();
                }
            }
        });
        textViewSignUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(LoginActivity.this)) {
                    Intent intent = new Intent(LoginActivity.this,
                            RegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(LoginActivity.this)) {
                    Intent intent = new Intent(LoginActivity.this,
                            ForgotPassWordActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
            }
        });

    }


    @Override
    public void showProgress() {
        emailTextInputLayout.setError(null);
        passWordTextInputLayout.setError(null);
        Utils.showProgressDialog(mProgressDialog, "", getString(R.string.please_wait));

    }

    @Override
    public void hideProgress() {
        Utils.hideProgressDialog(mProgressDialog);
    }

    @Override
    public void validationError(String msg) {
        switch (msg) {
            case "Email Field":
                emailTextInputLayout.setError("Field cannot be empty");
                emailTextInputLayout.requestFocus();
                break;
            case "Password Field":
                emailTextInputLayout.setError(null);
                passWordTextInputLayout.setError("Field cannot be empty");
                passWordTextInputLayout.requestFocus();
                break;
            case "Email":
                passWordTextInputLayout.setError(null);
                emailTextInputLayout.setError("Not valid email address");
                emailTextInputLayout.requestFocus();
                break;
        }

    }


    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onLoadLoginData(LoginModel loginModel) {
        if (loginModel != null) {
            if (loginModel.getStatus().equals("success")) {

                finishLastActivity();

                SharedPrefsUtils.setStringPreference(LoginActivity.this, KEY_TOKEN,
                        loginModel.getData().getAuthToken());
                Intent intentRestaurentList = new Intent(LoginActivity.this,
                        MainRestaurantListingActivity.class);
                startActivity(intentRestaurentList);
                overridePendingTransition(R.anim.right, R.anim.left);
                LoginActivity.this.finish();
            }

        }
    }

    private void finishLastActivity() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "finish");
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}