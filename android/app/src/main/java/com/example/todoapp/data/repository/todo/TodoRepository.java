package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.di.qualifier.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoRepository implements TodoDataSource {

    private final TodoDataSource mTodoCacheDataSource;

    @Inject
    public TodoRepository(@Remote TodoDataSource mTodoCacheDataSource) {
        this.mTodoCacheDataSource = mTodoCacheDataSource;
    }

    @Override
    public void getTodoList(GetTodoListCallback callback) {
        mTodoCacheDataSource.getTodoList(callback);
    }

    @Override
    public void addTodo(Todo todo, AddTodoCallback callback) {
        mTodoCacheDataSource.addTodo(todo, callback);
    }

    @Override
    public void deleteTodo(String id, DeleteTodoCallback callback) {
        mTodoCacheDataSource.deleteTodo(id, callback);
    }
}
