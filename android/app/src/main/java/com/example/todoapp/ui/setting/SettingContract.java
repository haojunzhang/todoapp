package com.example.todoapp.ui.setting;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface SettingContract {
    interface View extends BaseView {
        void showEmailText(String text);

        void logout();

        void openSplashActivity();
    }

    interface Presenter extends BasePresenter {
        void showUI();

        void logout();
    }
}
