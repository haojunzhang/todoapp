package com.example.todoapp.di.module;

import com.example.todoapp.data.network.todoapp.ITodoService;
import com.example.todoapp.utils.NativeUtils;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {

    @Provides
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient mOkHttpClient){
        return new Retrofit.Builder()
                .baseUrl(NativeUtils.getString(NativeUtils.URL))
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    ITodoService provideITodoService(Retrofit mRetrofit){
        return mRetrofit.create(ITodoService.class);
    }
}
