package com.example.todoapp.ui.forgotpassword;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ForgotPasswordModule {

    @Binds
    abstract ForgotPasswordContract.Presenter provideForgotPasswordPresenter(ForgotPasswordPresenter mForgotPasswordPresenter);

    @Binds
    abstract ForgotPasswordContract.View provideForgotPasswordView(ForgotPasswordActivity mForgotPasswordActivity);

}
