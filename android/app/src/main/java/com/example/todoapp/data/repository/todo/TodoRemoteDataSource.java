package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.BaseTodoServiceCallBack;
import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.response.GetTodoListResponse;

import java.util.Collections;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoRemoteDataSource implements TodoDataSource {

    private final TodoService mTodoService;

    public TodoRemoteDataSource(TodoService mTodoService) {
        this.mTodoService = mTodoService;
    }

    @Override
    public void getTodoList(int page, GetTodoListCallback callback) {
        mTodoService.getTodoList(page, new BaseTodoServiceCallBack<GetTodoListResponse>(GetTodoListResponse.class) {
            @Override
            public void onSuccess(GetTodoListResponse response) {

            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {

            }
        });
    }

    @Override
    public void addTodo(Todo todo, AddTodoCallback callback) {

    }

    @Override
    public void deleteTodo(String id, DeleteTodoCallback callback) {

    }
}
