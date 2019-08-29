package com.example.todoapp.ui.splash;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SplashModule {

    @Binds
    abstract SplashContract.Presenter provideSplashPresenter(SplashPresenter mSplashPresenter);

    @Binds
    abstract SplashContract.View provideSplashView(SplashActivity homeActivity);

}
