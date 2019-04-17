package com.restaurant.manasa.restauranthub.ui.login_or_register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.restaurant.manasa.restauranthub.R;
import com.restaurant.manasa.restauranthub.ui.login.LoginActivity;
import com.restaurant.manasa.restauranthub.ui.register.RegisterActivity;

public class LoginOrRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        initUi();
    }

    private void initUi() {

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginOrRegisterActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });

        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginOrRegisterActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result.equals("finish")) this.finish();
            }
        }
    }
}
