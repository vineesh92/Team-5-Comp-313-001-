package com.restaurant.manasa.restauranthub.ui.reset_password;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.ResetPasswordModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantResetPasswordContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;


public class ResetPasswordActivity extends BaseActivity implements RestaurantResetPasswordContract.View {
    private EditText edtPasswordView;
    private EditText edtConfirmPasswordView;
    private RestaurantResetPasswordPresenter restaurantResetPasswordPresenter;
    private ProgressDialog mProgressDialog;
    private String title;
    private TextInputLayout mNewPasswordLayout, mConfirmNewPasswordLayout;
    private AlertDialog alert11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            //TODO here get the string stored in the string variable and do
            if (bundle.getString("title") != null) {
                title = bundle.getString("title");
            }
        }
        initUi();

        restaurantResetPasswordPresenter = new RestaurantResetPasswordPresenter(
                new RestaurantResetPasswordInteractor(),
                ResetPasswordActivity.this);

        mProgressDialog = new ProgressDialog(ResetPasswordActivity.this,
                R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    private void initUi() {
//        Toolbar toolBar = findViewById(R.id.toolBar);
        TextView txtForgotPasswordView = findViewById(R.id.txtForgotPasswordView);
        edtPasswordView = findViewById(R.id.edtpasswordView);
        edtConfirmPasswordView = findViewById(R.id.edtConfirmPasswordView);
        Button btnResetView = findViewById(R.id.btnResetView);
        mNewPasswordLayout = findViewById(R.id.layout_new_password);
        mConfirmNewPasswordLayout = findViewById(R.id.layout_confirm_new_password);

        if (title != null && title.equals("from_my_account")) {
//            txtForgotPasswordView.setText("Change Password");
//            btnResetView.setText("Save");
            findViewById(R.id.layout_oldPassword).setVisibility(View.VISIBLE);
        }

        btnResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(ResetPasswordActivity.this)) {
                    String mToken = SharedPrefsUtils.getStringPreference(ResetPasswordActivity.this,
                            KEY_TOKEN, "");
                    String mPassWord = edtPasswordView.getText().toString().trim();
                    String mConformPassWord = edtConfirmPasswordView.getText().toString().trim();
                    restaurantResetPasswordPresenter.requestResetPasssWord(mToken, mPassWord, mConformPassWord);
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
        edtPasswordView.setError(null);
        edtConfirmPasswordView.setError(null);
        Utils.showProgressDialog(mProgressDialog, "", getString(R.string.please_wait));

    }

    @Override
    public void hideProgress() {
        Utils.hideProgressDialog(mProgressDialog);
    }

    @Override
    public void validationError(String msg) {
        switch (msg) {
            case "Password":
                mNewPasswordLayout.setError("Field cannot be empty");
                break;
            case "Confirm Password":
                mNewPasswordLayout.setError(null);
                mConfirmNewPasswordLayout.setError("Field cannot be empty");
                break;
            case "Password does not match":
                mConfirmNewPasswordLayout.setError(null);
                Toast.makeText(ResetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onResetPasswordResponse(ResetPasswordModel resetPasswordModel) {

        if (resetPasswordModel != null && resetPasswordModel.getStatus() != null) {
            if (resetPasswordModel.getStatus().equals("success")) {
//                Intent intent = new Intent(ResetPasswordActivity.this,
//                        MainRestaurantListingActivity.class);
//                intent.putExtra("exit", "exit");
//                setResult(RESULT_OK, intent);
//                startActivity(intent);
//                overridePendingTransition(R.anim.right, R.anim.left);
//                ResetPasswordActivity.this.finish();
                Utils.hideKeyboard(ResetPasswordActivity.this);
                onBackPressed();
                String msg;
                if (title != null) msg = "Password changed successfully";
                else msg = "Password reset successfull";

                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


            }
        }
    }

 
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(ResetPasswordActivity.this,
                R.style.AppCompatAlertDialogStyle);
        builder1.setMessage("Do you want to go back?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        finish();
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert11 = builder1.create();
        alert11.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alert11 != null && alert11.isShowing()) {
            alert11.dismiss();
        }
    }
}