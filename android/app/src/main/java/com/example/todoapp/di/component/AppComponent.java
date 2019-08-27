package com.example.todoapp.di.component;

import com.example.todoapp.di.module.ActivityModule;
import com.example.todoapp.di.module.AppModule;
import com.example.todoapp.di.module.DataSourceModule;
import com.example.todoapp.di.module.NetworkModule;
import com.example.todoapp.ui.base.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                NetworkModule.class,
                DataSourceModule.class,
                ActivityModule.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);

        AppComponent build();
    }

    void inject(App app);
}
