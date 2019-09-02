package com.example.todoapp.ui.signup;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface SignUpContract {
    interface View extends BaseView {
        void showSendOtpMessage();

        void openMainActivity();

        void finishAllActivity();
    }

    interface Presenter extends BasePresenter {
        void setOtpId(String otpId);

        void sendOtp(String email);

        void signUp(String email, String password, String passwordConfirm, String otp);
    }
}
