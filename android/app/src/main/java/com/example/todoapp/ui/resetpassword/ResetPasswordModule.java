package com.example.todoapp.ui.resetpassword;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResetPasswordModule {

    @Binds
    abstract ResetPasswordContract.Presenter provideResetPasswordPresenter(ResetPasswordPresenter mResetPasswordPresenter);

    @Binds
    abstract ResetPasswordContract.View provideResetPasswordView(ResetPasswordActivity mResetPasswordActivity);

}
