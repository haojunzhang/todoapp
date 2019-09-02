package com.example.todoapp.ui.signup;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SignUpModule {

    @Binds
    abstract SignUpContract.Presenter provideSignUpPresenter(SignUpPresenter mSignUpPresenter);

    @Binds
    abstract SignUpContract.View provideSignUpView(SignUpActivity mSignUpActivity);

}
