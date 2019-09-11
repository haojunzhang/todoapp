package com.example.todoapp.di.module;

import android.content.SharedPreferences;

import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.LogoutUtils;
import com.example.todoapp.utils.PrefUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    LogoutUtils provideLogoutUtils(UserRepository mUserRepository, AppRepository mAppRepository,
                                   KeyStoreRepository mKeyStoreUtils, TodoService mTodoService) {
        return new LogoutUtils(mUserRepository, mAppRepository,
                mKeyStoreUtils, mTodoService);
    }

    @Provides
    PrefUtils providePrefUtils(SharedPreferences mSharedPreferences) {
        return new PrefUtils(mSharedPreferences);
    }
}
