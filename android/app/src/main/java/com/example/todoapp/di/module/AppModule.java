package com.example.todoapp.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.todoapp.data.repository.keystore.KeyStoreDefaultDataSource;
import com.example.todoapp.base.App;

import java.security.KeyStore;
import java.security.KeyStoreException;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext() {
        return App.getContext();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("todo_app", Context.MODE_PRIVATE);
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
