package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.di.qualifier.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoRepository implements TodoDataSource {

    public static final int PAGE_SIZE_LOAD_AT_ONCE = 10;

    private final TodoDataSource mTodoRemoteDataSource;

    private List<Todo> todoList;

    @Inject
    public TodoRepository(@Remote TodoDataSource mTodoRemoteDataSource) {
        this.mTodoRemoteDataSource = mTodoRemoteDataSource;
    }

    @Override
    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public List<Todo> getTodoList() {
        return todoList;
    }

    @Override
    public void getTodoList(int page, GetTodoListCallback callback) {
        mTodoRemoteDataSource.getTodoList(page, callback);
    }

    @Override
    public void addTodo(String content, AddTodoCallback callback) {
        mTodoRemoteDataSource.addTodo(content, callback);
    }

    @Override
    public void deleteTodo(String id, DeleteTodoCallback callback) {
        mTodoRemoteDataSource.deleteTodo(id, callback);
    }
}
