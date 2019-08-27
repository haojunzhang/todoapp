package com.example.todoapp.di.module;

import android.content.Context;

import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.repository.app.AppDataSource;
import com.example.todoapp.data.repository.app.AppPrefDataSource;
import com.example.todoapp.data.repository.app.AppRemoteDataSource;
import com.example.todoapp.data.repository.keystore.KeyStoreDataSource;
import com.example.todoapp.data.repository.keystore.KeyStoreDefaultDataSource;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.todo.TodoDataSource;
import com.example.todoapp.data.repository.todo.TodoRemoteDataSource;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserPrefDataSource;
import com.example.todoapp.data.repository.user.UserRemoteDataSource;
import com.example.todoapp.di.qualifier.Default;
import com.example.todoapp.di.qualifier.Pref;
import com.example.todoapp.di.qualifier.Remote;
import com.example.todoapp.utils.PrefUtils;

import java.security.KeyStore;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {
    @Provides
    @Default
    KeyStoreDataSource provideKeyStoreDefaultDataSource(Context mContext, KeyStore mKeyStore) {
        return new KeyStoreDefaultDataSource(mContext, mKeyStore);
    }

    @Provides
    @Pref
    AppDataSource provideAppPrefDataSource(PrefUtils mPrefUtils, KeyStoreRepository mKeyStoreUtils) {
        return new AppPrefDataSource(mPrefUtils, mKeyStoreUtils);
    }

    @Provides
    @Remote
    AppDataSource provideAppRemoteDataSource() {
        return new AppRemoteDataSource();
    }

    @Provides
    @Pref
    UserDataSource provideUserPrefDataSource(PrefUtils mPrefUtils, KeyStoreRepository mKeyStoreUtils) {
        return new UserPrefDataSource(mPrefUtils, mKeyStoreUtils);
    }

    @Provides
    @Remote
    UserDataSource provideUserRemoteDataSource(TodoService mTodoService) {
        return new UserRemoteDataSource(mTodoService);
    }

    @Provides
    @Remote
    TodoDataSource provideTodoRemoteDataSource(TodoService mTodoService){
        return new TodoRemoteDataSource();
    }
}
