package com.example.todoapp.ui.splash;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void openMainActivity();

        void openLoginActivity();

        void finishActivity();
    }

    interface Presenter extends BasePresenter {
        void checkLoginStatus();
    }
}
