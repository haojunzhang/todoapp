package com.example.todoapp.di.component;

import com.example.todoapp.di.module.ActivityModule;
import com.example.todoapp.di.module.AppModule;
import com.example.todoapp.di.module.DataSourceModule;
import com.example.todoapp.di.module.FragmentModule;
import com.example.todoapp.di.module.NetworkModule;
import com.example.todoapp.di.module.UtilsModule;
import com.example.todoapp.base.App;
import com.example.todoapp.di.module.ViewModelModule;

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
                UtilsModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ViewModelModule.class
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
