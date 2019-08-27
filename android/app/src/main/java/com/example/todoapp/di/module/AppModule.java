package com.example.todoapp.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.todoapp.data.repository.keystore.KeyStoreDefaultDataSource;
import com.example.todoapp.ui.base.App;
import com.example.todoapp.utils.LogoutUtils;
import com.example.todoapp.utils.PrefUtils;

import java.security.KeyStore;
import java.security.KeyStoreException;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    LogoutUtils provideLogoutUtils() {
        return new LogoutUtils();
    }

    @Provides
    Context provideContext() {
        return App.getContext();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("todo_app", Context.MODE_PRIVATE);
    }

    @Provides
    PrefUtils providePrefUtils(SharedPreferences mSharedPreferences) {
        return new PrefUtils(mSharedPreferences);
    }

    @Provides
    KeyStore provideKeyStore() {
        try {
            return KeyStore.getInstance(KeyStoreDefaultDataSource.KEYSTORE_PROVIDER);
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

}
