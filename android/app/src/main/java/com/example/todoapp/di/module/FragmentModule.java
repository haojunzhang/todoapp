package com.example.todoapp.di.module;

import com.example.todoapp.ui.home.HomeFragment;
import com.example.todoapp.ui.home.HomeModule;
import com.example.todoapp.ui.setting.SettingFragment;
import com.example.todoapp.ui.setting.SettingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment provideHomeFragment();

    @ContributesAndroidInjector(modules = SettingModule.class)
    abstract SettingFragment provideSettingFragment();

}
