package com.example.todoapp.ui.resetpassword;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.StringUtils;

import javax.inject.Inject;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {

    private final ResetPasswordContract.View mView;
    private final UserRepository mUserRepository;

    // extra
    private String otpId;

    @Inject
    public ResetPasswordPresenter(ResetPasswordContract.View mView, UserRepository mUserRepository) {
        this.mView = mView;
        this.mUserRepository = mUserRepository;
    }

    @Override
    public void setExtra(String otpId) {
        this.otpId = otpId;
    }

    @Override
    public void resetPassword(String password, String passwordConfirm, String otp) {
        if (StringUtils.isEmpty(password, passwordConfirm, otp)) {
            return;
        }

        if (!password.equals(passwordConfirm)) {
            return;
        }

        mView.showLoadingView();
        mUserRepository.resetPassword(password, otpId, otp, new UserDataSource.ResetPasswordCallbackI() {
            @Override
            public void onResetPassword() {
                mView.dismissLoadingView();
                mView.showSuccessMessage();
                mView.finishActivity();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoPocketServiceError(throwable, errorResponse);
            }
        });
    }
}
