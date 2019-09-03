package com.example.todoapp.ui.forgotpassword;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.StringUtils;

import javax.inject.Inject;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private final ForgotPasswordContract.View mView;
    private final UserRepository mUserRepository;

    @Inject
    public ForgotPasswordPresenter(ForgotPasswordContract.View mView, UserRepository mUserRepository) {
        this.mView = mView;
        this.mUserRepository = mUserRepository;
    }

    @Override
    public void forgotPassword(String email) {

        if (StringUtils.isEmpty(email)){
            return;
        }

        mView.showLoadingView();
        mUserRepository.sendOtp(email, new UserDataSource.SendOtpCallbackI() {
            @Override
            public void onSendOtp(String otpId) {
                mView.dismissLoadingView();
                mView.openResetPasswordActivity(otpId);
                mView.finishActivity();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoServiceError(throwable, errorResponse);
            }
        });
    }
}
