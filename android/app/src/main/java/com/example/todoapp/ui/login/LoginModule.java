package com.example.todoapp.ui.login;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginModule {

    @Binds
    abstract LoginContract.Presenter provideLoginPresenter(LoginPresenter mLoginPresenter);

    @Binds
    abstract LoginContract.View provideLoginView(LoginActivity homeActivity);

}
