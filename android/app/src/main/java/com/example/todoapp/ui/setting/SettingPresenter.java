package com.example.todoapp.ui.setting;

import com.example.todoapp.data.entity.UserProfile;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
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
        UserProfile userProfile = mUserRepository.getUserProfile();

        mView.showEmailText(userProfile.getEmail());
    }

    @Override
    public void getUserProfile() {
        mView.showLoadingView();
        mUserRepository.getUserProfile(new UserDataSource.GetProfileCallback() {
            @Override
            public void onGetProfile(String userId, String email) {
                mView.dismissLoadingView();

                UserProfile userProfile = new UserProfile();
                userProfile.setEmail(email);
                mUserRepository.setUserProfile(userProfile);

                mView.showEmailText(userProfile.getEmail());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoServiceError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void logout() {
        mView.logout();
        mView.openSplashActivity();
        mView.finishActivity();
    }
}
