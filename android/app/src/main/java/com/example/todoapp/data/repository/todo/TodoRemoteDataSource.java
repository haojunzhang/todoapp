package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;

import java.util.Collections;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoRemoteDataSource implements TodoDataSource {

    @Inject
    public TodoRemoteDataSource() {

    }

    private String getNextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public void getTodoList(GetTodoListCallback callback) {
        callback.onGetTodoList(Collections.emptyList());
    }

    @Override
    public void addTodo(Todo todo, AddTodoCallback callback) {
        callback.onAddTodo(false, todo);
    }

    @Override
    public void deleteTodo(String id, DeleteTodoCallback callback) {
        boolean success = false;
        callback.onDeleteTodo(success);
    }
}
