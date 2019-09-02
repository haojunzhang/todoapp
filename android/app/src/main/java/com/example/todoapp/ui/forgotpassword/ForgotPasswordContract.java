package com.example.todoapp.ui.forgotpassword;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface ForgotPasswordContract {
    interface View extends BaseView {
        void openResetPasswordActivity(String otpId);
    }

    interface Presenter extends BasePresenter {
        void forgotPassword(String email);
    }
}
