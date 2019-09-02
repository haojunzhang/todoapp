package com.example.todoapp.di.module;


import com.example.todoapp.ui.forgotpassword.ForgotPasswordActivity;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordModule;
import com.example.todoapp.ui.home.HomeActivity;
import com.example.todoapp.ui.home.HomeModule;
import com.example.todoapp.ui.login.LoginActivity;
import com.example.todoapp.ui.login.LoginModule;
import com.example.todoapp.ui.main.MainActivity;
import com.example.todoapp.ui.resetpassword.ResetPasswordActivity;
import com.example.todoapp.ui.resetpassword.ResetPasswordModule;
import com.example.todoapp.ui.signup.SignUpActivity;
import com.example.todoapp.ui.signup.SignUpModule;
import com.example.todoapp.ui.splash.SplashActivity;
import com.example.todoapp.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity provideHomeActivity();

    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity provideSplashActivity();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity provideLoginActivity();

    @ContributesAndroidInjector(modules = SignUpModule.class)
    abstract SignUpActivity provideSignUpActivity();

    @ContributesAndroidInjector(modules = ForgotPasswordModule.class)
    abstract ForgotPasswordActivity provideForgotPasswordActivity();

    @ContributesAndroidInjector(modules = ResetPasswordModule.class)
    abstract ResetPasswordActivity provideResetPasswordActivity();

    @ContributesAndroidInjector
    abstract MainActivity provideMainActivity();

}
