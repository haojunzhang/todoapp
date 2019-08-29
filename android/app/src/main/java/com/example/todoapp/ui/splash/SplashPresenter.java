package com.example.todoapp.ui.splash;

import com.example.todoapp.data.repository.app.AppRepository;

import javax.inject.Inject;

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View mView;
    private final AppRepository mAppRepository;

    @Inject
    public SplashPresenter(SplashContract.View view, AppRepository mAppRepository) {
        mView = view;
        this.mAppRepository = mAppRepository;
    }

    @Override
    public void checkLoginStatus() {
        if (mAppRepository.isLogin()) {
            mView.openMainActivity();
            mView.finishActivity();
        } else {
            mView.openLoginActivity();
            mView.finishActivity();
        }
    }
}
