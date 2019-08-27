package com.example.todoapp.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.todoapp.data.network.todoapp.CallBackUtil;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.di.component.DaggerAppComponent;
import com.example.todoapp.utils.PrefUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    private static App instance;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    PrefUtils mPrefUtils;

    @Inject
    KeyStoreRepository mKeyStoreRepository;

    @Inject
    AppRepository mAppRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // DI
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        // 初始化key
        new Thread(() -> {
            String iv = mPrefUtils.getString(PrefUtils.IV);
            String aesKey = mPrefUtils.getString(PrefUtils.AES_KEY);
            if (iv == null || aesKey == null) {
                // 重新產生Pref的加解密key
                String[] ivAndAESKey = mKeyStoreRepository.generateKeyStoreRSAAndIVAndAESKey();
                mAppRepository.setIV(ivAndAESKey[0]);
                mAppRepository.setAESKey(ivAndAESKey[1]);
                mKeyStoreRepository.setIV(ivAndAESKey[0]);
                mKeyStoreRepository.setAESKey(ivAndAESKey[1]);
            } else {
                mKeyStoreRepository.setIV(iv);
                mKeyStoreRepository.setAESKey(aesKey);
            }
        }).start();

        // 初始化Logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .tag(CallBackUtil.TAG)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
