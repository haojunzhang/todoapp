package com.example.todoapp.ui.resetpassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;
import com.example.todoapp.utils.StringUtils;

public class ResetPasswordViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    private final MutableLiveData<Boolean> mIsResetPasswordSuccess = new MutableLiveData<>();

    // extra
    private String otpId;

    public ResetPasswordViewModel(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }

    public LiveData<Boolean> isResetPasswordSuccess(){
        return mIsResetPasswordSuccess;
    }

    public void start(String otpId) {
        this.otpId = otpId;
    }

    public void resetPassword(String password, String passwordConfirm, String otp) {
        if (StringUtils.isEmpty(password, passwordConfirm, otp)) {
            return;
        }

        if (!password.equals(passwordConfirm)) {
            return;
        }

        mIsLoading.setValue(true);
        mUserRepository.resetPassword(password, otpId, otp, new UserDataSource.ResetPasswordCallback() {
            @Override
            public void onResetPassword() {
                mIsLoading.setValue(false);
                mIsResetPasswordSuccess.setValue(true);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }
}
