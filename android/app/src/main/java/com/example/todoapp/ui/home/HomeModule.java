package com.example.todoapp.ui.home;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Presenter provideHomePresenter(HomePresenter mHomePresenter);

    @Binds
    abstract HomeContract.View provideHomeView(HomeFragment mHomeFragment);

}
