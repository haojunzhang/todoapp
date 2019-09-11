package com.example.todoapp.di.module;


import com.example.todoapp.ui.addtodo.AddTodoActivity;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordActivity;
import com.example.todoapp.ui.login.LoginActivity;
import com.example.todoapp.ui.main.MainActivity;
import com.example.todoapp.ui.resetpassword.ResetPasswordActivity;
import com.example.todoapp.ui.signup.SignUpActivity;
import com.example.todoapp.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity provideSplashActivity();

    @ContributesAndroidInjector
    abstract LoginActivity provideLoginActivity();

    @ContributesAndroidInjector
    abstract SignUpActivity provideSignUpActivity();

    @ContributesAndroidInjector
    abstract ForgotPasswordActivity provideForgotPasswordActivity();

    @ContributesAndroidInjector
    abstract ResetPasswordActivity provideResetPasswordActivity();

    @ContributesAndroidInjector
    abstract AddTodoActivity provideAddTodoActivity();

    @ContributesAndroidInjector
    abstract MainActivity provideMainActivity();

}
