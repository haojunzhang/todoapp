package com.example.todoapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;
import com.example.todoapp.ui.login.LoginActivity;
import com.example.todoapp.ui.main.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash_activity);
        super.onCreate(savedInstanceState);

        mPresenter.checkLoginStatus();
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
