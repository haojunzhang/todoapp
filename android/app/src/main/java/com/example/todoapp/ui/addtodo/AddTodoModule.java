package com.example.todoapp.ui.addtodo;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AddTodoModule {

    @Binds
    abstract AddTodoContract.Presenter provideAddTodoPresenter(AddTodoPresenter mAddTodoPresenter);

    @Binds
    abstract AddTodoContract.View provideAddTodoView(AddTodoActivity mAddTodoActivity);
}
