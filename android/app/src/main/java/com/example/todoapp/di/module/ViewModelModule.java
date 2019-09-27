package com.example.todoapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.todoapp.di.qualifier.ViewModelKey;
import com.example.todoapp.mvvm.ViewModelFactory;
import com.example.todoapp.ui.addtodo.AddTodoViewModel;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordViewModel;
import com.example.todoapp.ui.home.HomeViewModel;
import com.example.todoapp.ui.login.LoginViewModel;
import com.example.todoapp.ui.resetpassword.ResetPasswordViewModel;
import com.example.todoapp.ui.setting.SettingViewModel;
import com.example.todoapp.ui.signup.SignUpViewModel;
import com.example.todoapp.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(AddTodoViewModel.class)
    abstract ViewModel bindAddTodoViewModel(AddTodoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel.class)
    abstract ViewModel bindForgotPasswordViewModel(ForgotPasswordViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ResetPasswordViewModel.class)
    abstract ViewModel bindResetPasswordViewModel(ResetPasswordViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel.class)
    abstract ViewModel bindSettingViewModel(SettingViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);




}
