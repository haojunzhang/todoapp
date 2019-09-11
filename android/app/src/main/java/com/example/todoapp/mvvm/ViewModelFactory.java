package com.example.todoapp.mvvm;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.todo.TodoRepository;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.ui.addtodo.AddTodoViewModel;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordViewModel;
import com.example.todoapp.ui.home.HomeViewModel;
import com.example.todoapp.ui.login.LoginViewModel;
import com.example.todoapp.ui.resetpassword.ResetPasswordViewModel;
import com.example.todoapp.ui.setting.SettingViewModel;
import com.example.todoapp.ui.signup.SignUpViewModel;
import com.example.todoapp.ui.splash.SplashViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @Inject
    AppRepository mAppRepository;

    @Inject
    KeyStoreRepository mKeyStoreRepository;

    @Inject
    TodoRepository mTodoRepository;

    @Inject
    UserRepository mUserRepository;

    @Inject
    public ViewModelFactory() {

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(mAppRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mUserRepository, mAppRepository, mKeyStoreRepository);
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(mAppRepository, mKeyStoreRepository, mUserRepository);
        } else if (modelClass.isAssignableFrom(AddTodoViewModel.class)) {
            return (T) new AddTodoViewModel(mTodoRepository);
        } else if (modelClass.isAssignableFrom(ForgotPasswordViewModel.class)) {
            return (T) new ForgotPasswordViewModel(mUserRepository);
        } else if (modelClass.isAssignableFrom(ResetPasswordViewModel.class)) {
            return (T) new ResetPasswordViewModel(mUserRepository);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mTodoRepository);
        } else if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(mUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
