package com.example.todoapp.ui.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.base.BaseViewModel;

public class SplashViewModel extends BaseViewModel {

    private final AppRepository mAppRepository;

    private final MutableLiveData<Boolean> mIsLogin = new MutableLiveData<>();

    public SplashViewModel(AppRepository mAppRepository) {
        this.mAppRepository = mAppRepository;
    }

    public LiveData<Boolean> isLogin() {
        return mIsLogin;
    }

    public void checkLoginStatus() {
        if (mAppRepository.isLogin()) {
            mIsLogin.setValue(true);
        } else {
            mIsLogin.setValue(false);
        }
    }
}
