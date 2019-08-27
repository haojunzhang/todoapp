package com.example.todoapp.di.module;


import com.example.todoapp.ui.home.HomeActivity;
import com.example.todoapp.ui.home.HomeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity provideHomeActivity();

}
