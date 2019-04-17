package com.restaurant.manasa.restauranthub.ui.register;

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
import com.restaurant.manasa.restauranthub.mvp.RestaurantRegisterContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.login.LoginActivity;
import com.restaurant.manasa.restauranthub.utils.Utils;



public class RegisterActivity extends BaseActivity implements RestaurantRegisterContract.View {
    private EditText edtTextFirstNameView;
    private EditText edtMobileNumberView;
    private EditText edtTextEmailView;
    private EditText edtTextPasswordView;
    private ProgressDialog mProgressDialog;
    private RestaurantRegisterPresenter restaurantRegisterPresenter;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout phoneTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inItUi();

        restaurantRegisterPresenter = new RestaurantRegisterPresenter(new RestaurantRegisterInteractor(),
                RegisterActivity.this);

        mProgressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    private void inItUi() {
//        Toolbar toolBar = findViewById(R.id.toolBar);
        edtTextFirstNameView = findViewById(R.id.edtTextFirstNameView);
        edtMobileNumberView = findViewById(R.id.edtMobileNumberView);
        edtTextEmailView = findViewById(R.id.edtTextEmailView);
        edtTextPasswordView = findViewById(R.id.edtTextPasswordView);
        TextView btnSignUpView = findViewById(R.id.btnSignUpView);

        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        phoneTextInputLayout = findViewById(R.id.phoneTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

        btnSignUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(RegisterActivity.this)) {

                    String mName = edtTextFirstNameView.getText().toString().trim();
                    String mPhoneNumber = edtMobileNumberView.getText().toString().trim();
                    String mEmail = edtTextEmailView.getText().toString().trim();
                    String mPassword = edtTextPasswordView.getText().toString().trim();

                    restaurantRegisterPresenter.requestRegister(mName, mPhoneNumber, mEmail, mPassword);
                } else {
                    showSnackBar();
                }
            }
        });
//        toolBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
    }

    
    @Override
    public void showProgress() {
        nameTextInputLayout.setError(null);
        phoneTextInputLayout.setError(null);
        emailTextInputLayout.setError(null);
        passwordTextInputLayout.setError(null);
        Utils.showProgressDialog(mProgressDialog, "", getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        Utils.hideProgressDialog(mProgressDialog);
    }

    @Override
    public void validationError(String msg) {
        switch (msg) {
            case "Full Name empty":
                nameTextInputLayout.setError("Field cannot be empty");
                break;
            case "Mobile Number empty":
                nameTextInputLayout.setError(null);
                phoneTextInputLayout.setError("Field cannot be empty");
                break;
            case "Email empty":
                phoneTextInputLayout.setError(null);
                emailTextInputLayout.setError("Field cannot be empty");
                break;
            case "Password empty":
                emailTextInputLayout.setError(null);
                passwordTextInputLayout.setError("Field cannot be empty");
                break;
            case "Mobile Number":
                phoneTextInputLayout.setError("Not Valid Number");
                break;
            case "Email":
                phoneTextInputLayout.setError(null);
                emailTextInputLayout.setError("Not valid email address");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onLoadRegisterData(LoginModel loginModel) {

        if (loginModel != null) {
            if (loginModel.getStatus().equals("success")) {

                finishLastActivity();

                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.left);
                RegisterActivity.this.finish();
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
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

}