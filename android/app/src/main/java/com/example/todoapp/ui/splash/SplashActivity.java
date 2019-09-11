package com.example.todoapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;
import com.example.todoapp.ui.login.LoginActivity;
import com.example.todoapp.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    SplashViewModel mSplashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash_activity);
        super.onCreate(savedInstanceState);

        initViewModel();

        mSplashViewModel.checkLoginStatus();
    }

    private void initViewModel() {
        mSplashViewModel = obtainViewModel(SplashViewModel.class);

        observeBaseLiveData(mSplashViewModel);

        mSplashViewModel.isLogin().observe(this, isLogin -> {
            if (isLogin) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        });
    }
}
