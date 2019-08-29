package com.example.todoapp.ui.login;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void openMainActivity();
    }

    interface Presenter extends BasePresenter {
        void login(String email, String password);
    }
}
