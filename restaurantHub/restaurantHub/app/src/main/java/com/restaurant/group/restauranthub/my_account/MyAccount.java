package com.restaurant.manasa.restauranthub.ui.my_account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.customViews.CircleTransform;
import com.restaurant.manasa.restauranthub.models.MyAccountModel;
import com.restaurant.manasa.restauranthub.models.SuccessModel;
import com.restaurant.manasa.restauranthub.mvp.RestaurantMyAccountContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.reset_password.ResetPasswordActivity;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;


public class MyAccount extends BaseActivity implements RestaurantMyAccountContract.View {

    private ImageView profileImageView;
    private EditText edtTextFirstNameView;
    private EditText edtMobileNumberView;
    private EditText edtTextEmailView;
    private RestaurantMyAccountPresenter mRestaurantMyAccountPresenter;
    private LinearLayout accountLinear;
    private LinearLayout progressbarLinear;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        inItUi();

        mRestaurantMyAccountPresenter = new RestaurantMyAccountPresenter(new RestaurantMyAccountInteractor(),
                MyAccount.this);
        String token = SharedPrefsUtils.getStringPreference(MyAccount.this,
                KEY_TOKEN, "");
        mRestaurantMyAccountPresenter.requestMyAccount(token);

        mProgressDialog = new ProgressDialog(MyAccount.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    private void inItUi() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        accountLinear = findViewById(R.id.accountLinear);
        progressbarLinear = findViewById(R.id.linear_layout_progressbar);


        profileImageView = findViewById(R.id.profileImageView);
        TextView changePasswordButton = findViewById(R.id.changePasswordButton);
        edtTextFirstNameView = findViewById(R.id.edtTextFirstNameView);
        edtMobileNumberView = findViewById(R.id.edtMobileNumberView);
        edtTextEmailView = findViewById(R.id.edtTextEmailView);
        LinearLayout btnLogout = findViewById(R.id.btnLogout);

        // TODO : Initially
//        edtTextFirstNameView.setFocusable(false);
//        edtMobileNumberView.setFocusable(false);
//        edtTextEmailView.setFocusable(false);
//        edtTextFirstNameView.setFocusableInTouchMode(false);
//        edtMobileNumberView.setFocusableInTouchMode(false);
//        edtTextEmailView.setFocusableInTouchMode(false);
//        edtTextFirstNameView.setClickable(false);
//        edtMobileNumberView.setClickable(false);
//        edtTextEmailView.setClickable(false);

        // TODO : Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(MyAccount.this)) {
                    String mToken = SharedPrefsUtils.getStringPreference(MyAccount.this,
                            KEY_TOKEN, "");
                    String mName = edtTextFirstNameView.getText().toString().trim();
                    String mPhoneNumber = edtMobileNumberView.getText().toString().trim();
                    String mEmail = edtTextEmailView.getText().toString().trim();
                    mRestaurantMyAccountPresenter.updateMyAccount(mToken, mName, mPhoneNumber, mEmail);

                } else {
                    showSnackBar();
                }
            }
        });

        // TODO : Edit Profile
//        profileEditImageView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onClick(View view) {
//                if (Utils.isNetworkAvailable(MyAccount.this)) {
//
//                    profileEditImageView.setImageResource(R.drawable.edit_icon_profile_disable);
//                    profileEditImageView.setClickable(false);
//                    profileEditImageView.setFocusableInTouchMode(false);
//                    profileEditImageView.setFocusable(false);
//                    profileEditImageView.setVisibility(View.GONE);
//
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            edtTextFirstNameView.requestFocus();
//                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            if (mgr != null) {
//                                mgr.showSoftInput(edtTextFirstNameView, InputMethodManager.SHOW_IMPLICIT);
//                            }
//
//                        }
//                    }, 100);
//
//                    edtTextFirstNameView.setFocusable(true);
//                    edtMobileNumberView.setFocusable(true);
//                    edtTextEmailView.setFocusable(true);
//
//                    edtTextFirstNameView.setFocusableInTouchMode(true);
//                    edtMobileNumberView.setFocusableInTouchMode(true);
//                    edtTextEmailView.setFocusableInTouchMode(true);
//
//                    edtTextFirstNameView.setClickable(true);
//                    edtMobileNumberView.setClickable(true);
//                    edtTextEmailView.setClickable(true);
//
//
//                } else {
//                    showSnackBar();
//                }
//            }
//        });

        // TODO : Change Password
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(MyAccount.this)) {
                    Intent intentRestaurantList = new Intent(MyAccount.this,
                            ResetPasswordActivity.class);
                    intentRestaurantList.putExtra("title", "from_my_account");
                    startActivity(intentRestaurantList);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else {
                    showSnackBar();
                }
            }
        });

        // TODO : Back press
        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        // TODO : Profile Image change
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(MyAccount.this)) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(MyAccount.this);
                } else {
                    showSnackBar();
                }
            }
        });


    }

 
    @Override
    public void showProgress() {
        accountLinear.setVisibility(View.GONE);
        progressbarLinear.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        accountLinear.setVisibility(View.VISIBLE);
        progressbarLinear.setVisibility(View.GONE);

    }

    @Override
    public void showProgressDialog() {
        Utils.showProgressDialog(mProgressDialog, "", getString(R.string.please_wait));

    }

    @Override
    public void hideProgressDialog() {
        Utils.hideProgressDialog(mProgressDialog);
    }

    @Override
    public void validationError(String msg) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onMyAccountResponse(MyAccountModel myAccountModel) {

        if (myAccountModel != null && myAccountModel.getData() != null) {

            if (myAccountModel.getData().getFirstName() != null) {
                edtTextFirstNameView.setText(myAccountModel.getData().getFirstName());
            }
            if (myAccountModel.getData().getPhoneNumber() != null) {
                edtMobileNumberView.setText(myAccountModel.getData().getPhoneNumber());
            }
            if (myAccountModel.getData().getEmail() != null) {
                edtTextEmailView.setText(myAccountModel.getData().getEmail());
            }
            if (myAccountModel.getData().getUserImage() != null &&
                    !myAccountModel.getData().getUserImage().equals("")) {
                Picasso.get()
                        .load(myAccountModel.getData().getUserImage())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.img_restaurant_no_image)
                        .into(profileImageView);
            }
        }
    }

    @Override
    public void onUpdateMyAccountResponse(SuccessModel sucessModel) {

        if (sucessModel != null && sucessModel.getStatus() != null
                && sucessModel.getStatus().equals("success")) {
            Utils.stylableToast(MyAccount.this, "Profile updated sucessfully");
            onBackPressed();

        }

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(this);
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Picasso.get()
                        .load(resultUri)
                        .transform(new CircleTransform())
                        .into(profileImageView);

                mRestaurantMyAccountPresenter.updateMyAccountImage(resultUri);
            }
        }
    }

}