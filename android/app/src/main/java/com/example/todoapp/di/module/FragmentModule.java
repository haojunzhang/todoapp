package com.example.todoapp.di.module;

import com.example.todoapp.ui.home.HomeFragment;
import com.example.todoapp.ui.setting.SettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract HomeFragment provideHomeFragment();

    @ContributesAndroidInjector
    abstract SettingFragment provideSettingFragment();

}
