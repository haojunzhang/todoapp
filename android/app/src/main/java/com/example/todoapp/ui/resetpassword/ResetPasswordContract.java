package com.example.todoapp.ui.resetpassword;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface ResetPasswordContract {
    interface View extends BaseView {
        void showSuccessMessage();
    }

    interface Presenter extends BasePresenter {
        void setExtra(String otpId);

        void resetPassword(String password, String passwordConfirm, String otp);
    }
}
