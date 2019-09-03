package com.example.todoapp.ui.setting;

import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.LogUtils;

import javax.inject.Inject;

public class SettingPresenter implements SettingContract.Presenter {

    private final SettingContract.View mView;
    private final UserRepository mUserRepository;

    @Inject
    public SettingPresenter(SettingContract.View mView, UserRepository mUserRepository) {
        this.mView = mView;
        this.mUserRepository = mUserRepository;
    }

    @Override
    public void showUI() {
        String email = mUserRepository.getEmail();
        mView.showEmailText(email);
    }

    @Override
    public void logout() {
        mView.logout();
        mView.openSplashActivity();
        mView.finishActivity();
    }
}
