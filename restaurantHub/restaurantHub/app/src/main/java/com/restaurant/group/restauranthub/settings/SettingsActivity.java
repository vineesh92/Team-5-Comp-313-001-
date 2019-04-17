package com.restaurant.manasa.restauranthub.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.restaurant.manasa.restauranthub.BuildConfig;
import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.customViews.CircleTransform;
import com.restaurant.manasa.restauranthub.models.MyAccountModel;
import com.restaurant.manasa.restauranthub.mvp.SettingsContract;
import com.restaurant.manasa.restauranthub.ui.BaseActivity;
import com.restaurant.manasa.restauranthub.ui.change_email.ChangeEmailActivity;
import com.restaurant.manasa.restauranthub.ui.change_password.ChangePasswordActivity;
import com.restaurant.manasa.restauranthub.ui.login.LoginActivity;
import com.restaurant.manasa.restauranthub.ui.my_account.MyAccount;
import com.restaurant.manasa.restauranthub.ui.my_account.RestaurantMyAccountInteractor;
import com.restaurant.manasa.restauranthub.utils.SharedPrefsUtils;
import com.restaurant.manasa.restauranthub.utils.Utils;
import com.squareup.picasso.Picasso;

import static com.restaurant.manasa.restauranthub.utils.AppConstants.KEY_TOKEN;

public class SettingsActivity extends BaseActivity implements SettingsContract.View, ViewGroup.OnClickListener {

    private SettingsContract.Presenter mSettingsPresenter;
    private ImageView mProfileImageView;
    private TextView mUsernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsPresenter = new SettingsPresenter(new RestaurantMyAccountInteractor(), SettingsActivity.this);

        initUI();

        mSettingsPresenter.requestAccountDetails("1");
    }

    private void initUI() {

        mProfileImageView = findViewById(R.id.image_settings_profile);
        mUsernameTextView = findViewById(R.id.text_setting_username);

        RelativeLayout mMyAccountLayout = findViewById(R.id.text_edit_profile);
//        LinearLayout mMyBookingsLayout = findViewById(R.id.layout_my_bookings);
        RelativeLayout mChangePasswordLayout = findViewById(R.id.layout_change_password);
        TextView mShareLayout = findViewById(R.id.layout_share);
        TextView mLogoutLayout = findViewById(R.id.layout_logout);

        ((TextView) findViewById(R.id.text_version)).setText(BuildConfig.VERSION_NAME);

        mMyAccountLayout.setOnClickListener(this);
//        mMyBookingsLayout.setOnClickListener(this);
        mChangePasswordLayout.setOnClickListener(this);
        mShareLayout.setOnClickListener(this);
        mLogoutLayout.setOnClickListener(this);
        findViewById(R.id.layout_change_email).setOnClickListener(this);


        android.support.v7.widget.Toolbar toolBar = findViewById(R.id.toolBar);
        toolBar.findViewById(R.id.toolbar_navigation_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    @Override
    public void loadMyAccountResponse(MyAccountModel myAccountModel) {

        Picasso.get()
                .load(myAccountModel.getData().getUserImage())
                .transform(new CircleTransform())
                .into(mProfileImageView);

        mUsernameTextView.setText(myAccountModel.getData().getFirstName());
    }

    @Override
    public void showProgress() {

        findViewById(R.id.linear_layout_progressbar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        findViewById(R.id.linear_layout_progressbar).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.text_edit_profile:
                if (Utils.isNetworkAvailable(SettingsActivity.this)) {
                    intent = new Intent(SettingsActivity.this, MyAccount.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
                break;

            case R.id.layout_change_email:
                if (Utils.isNetworkAvailable(SettingsActivity.this)) {
                    intent = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
                break;

            case R.id.layout_change_password:
                if (Utils.isNetworkAvailable(SettingsActivity.this)) {
                    intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("title", "from_my_account");
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.right, R.anim.left);
                } else showSnackBar();
                break;

            case R.id.layout_share:
                if (Utils.isNetworkAvailable(SettingsActivity.this)) {
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_app));
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "Send to"));
                } else showSnackBar();
                break;

            case R.id.layout_logout:

                new AlertDialog.Builder(SettingsActivity.this)
//                                    .setTitle("Logout")
                        .setMessage(getString(R.string.logout))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // logout
                                SharedPrefsUtils.setStringPreference(SettingsActivity.this,
                                        KEY_TOKEN, "");
                                dialog.dismiss();
                                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                                intent.putExtra("logout", "logout");
                                setResult(RESULT_OK, intent);
                                finish();
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSettingsPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (data != null && data.getStringExtra("exit") != null) {

                SettingsActivity.this.finish();
            }
        }
    }
}