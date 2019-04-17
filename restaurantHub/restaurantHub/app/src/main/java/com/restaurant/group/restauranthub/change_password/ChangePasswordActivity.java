package com.restaurant.manasa.restauranthub.ui.change_password;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.models.ResetPasswordModel;
import com.restaurant.manasa.restauranthub.mvp.ChangePasswordContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;


public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {
    private EditText edtPasswordView;
    private EditText edtConfirmPasswordView;
    private ChangePasswordPresenter restaurantResetPasswordPresenter;
    private ProgressDialog mProgressDialog;
    private String title;
    private TextInputLayout mOldPasswordLayout;
    private TextInputLayout mNewPasswordLayout;
    private TextInputLayout mConfirmNewPasswordLayout;
    private EditText edtOldPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            //TODO here get the string stored in the string variable and do
            if (bundle.getString("title") != null) {
                title = bundle.getString("title");
            }
        }
        initUi();

        restaurantResetPasswordPresenter = new ChangePasswordPresenter(
                new ChangePasswordInteractor(),
                ChangePasswordActivity.this);

        mProgressDialog = new ProgressDialog(ChangePasswordActivity.this,
                R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    private void initUi() {
//        Toolbar toolBar = findViewById(R.id.toolBar);
        TextView txtForgotPasswordView = findViewById(R.id.txtForgotPasswordView);
        edtOldPasswordView = findViewById(R.id.et_current_password);
        edtPasswordView = findViewById(R.id.edtpasswordView);
        edtConfirmPasswordView = findViewById(R.id.edtConfirmPasswordView);
        Button btnResetView = findViewById(R.id.btnResetView);
        mOldPasswordLayout = findViewById(R.id.layout_oldPassword);
        mNewPasswordLayout = findViewById(R.id.layout_new_password);
        mConfirmNewPasswordLayout = findViewById(R.id.layout_confirm_new_password);

        if (title != null && title.equals("from_my_account")) {
//            txtForgotPasswordView.setText("Change Password");
//            btnResetView.setText("Save");
            findViewById(R.id.layout_oldPassword).setVisibility(View.VISIBLE);
            Toolbar toolBar = findViewById(R.id.toolBar);
            toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();
                }
            });
        }

        btnResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(ChangePasswordActivity.this)) {
                    String mToken = SharedPrefsUtils.getStringPreference(ChangePasswordActivity.this,
                            KEY_TOKEN, "");
                    String mOldPassword = edtOldPasswordView.getText().toString().trim();
                    String mPassWord = edtPasswordView.getText().toString().trim();
                    String mConformPassWord = edtConfirmPasswordView.getText().toString().trim();
                    restaurantResetPasswordPresenter.requestResetPasssWord(mToken, mOldPassword, mPassWord, mConformPassWord);
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

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     **/
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
            case "Old Password":
                mOldPasswordLayout.setError("Field cannot be empty");
                break;
            case "Password":
                mOldPasswordLayout.setError(null);
                mNewPasswordLayout.setError("Field cannot be empty");
                break;
            case "Confirm Password":
                mNewPasswordLayout.setError(null);
                mConfirmNewPasswordLayout.setError("Field cannot be empty");
                break;
            case "Password does not match":
                mConfirmNewPasswordLayout.setError(null);
                Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Utils.hideKeyboard(this);
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
        Utils.hideKeyboard(this);
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}