package com.example.todoapp.ui.setting;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SettingModule {

    @Binds
    abstract SettingContract.Presenter provideSettingPresenter(SettingPresenter mSettingPresenter);

    @Binds
    abstract SettingContract.View provideSettingView(SettingFragment mSettingActivity);

}
