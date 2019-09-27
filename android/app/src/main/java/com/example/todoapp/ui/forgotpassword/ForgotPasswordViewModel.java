package com.example.todoapp.ui.forgotpassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;
import com.example.todoapp.utils.StringUtils;

import javax.inject.Inject;

public class ForgotPasswordViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    private final MutableLiveData<String> mOtpId = new MutableLiveData<>();

    @Inject
    public ForgotPasswordViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }

    public LiveData<String> getOtpId(){
        return mOtpId;
    }

    public void forgotPassword(String email) {

        if (StringUtils.isEmpty(email)) {
            return;
        }

        mIsLoading.setValue(true);
        mUserRepository.sendOtp(email, new UserDataSource.SendOtpCallback() {
            @Override
            public void onSendOtp(String otpId) {
                mIsLoading.setValue(false);
                mOtpId.setValue(otpId);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }
}
