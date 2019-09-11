package com.example.todoapp.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.entity.UserProfile;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;

public class SettingViewModel extends BaseViewModel {
    private final UserRepository mUserRepository;

    private final MutableLiveData<String> mEmail= new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsLogout= new MutableLiveData<>();

    public SettingViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }

    public LiveData<String> getEmail(){
        return mEmail;
    }

    public LiveData<Boolean> isLogout(){
        return mIsLogout;
    }

    public void start(){
        UserProfile userProfile = mUserRepository.getUserProfile();

        mEmail.setValue(userProfile.getEmail());
    }

    public void getUserProfile() {
        mIsLoading.setValue(true);
        mUserRepository.getUserProfile(new UserDataSource.GetProfileCallback() {
            @Override
            public void onGetProfile(String userId, String email) {
                mIsLoading.setValue(false);

                UserProfile userProfile = new UserProfile();
                userProfile.setEmail(email);
                mUserRepository.setUserProfile(userProfile);

                mEmail.setValue(userProfile.getEmail());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }

    public void logout() {
        mIsLogout.setValue(true);
    }
}
