package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.BaseTodoServiceCallBack;
import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.response.AddTodoResponse;
import com.example.todoapp.data.network.todoapp.response.GetTodoListResponse;

import java.util.Collections;
import java.util.List;
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
    public void setTodoList(List<Todo> todoList) {
        // do nothing
    }

    @Override
    public List<Todo> getTodoList() {
        // do nothing
        return null;
    }

    @Override
    public void getTodoList(int page, GetTodoListCallback callback) {
        mTodoService.getTodoList(page, new BaseTodoServiceCallBack<GetTodoListResponse>(GetTodoListResponse.class) {
            @Override
            public void onSuccess(GetTodoListResponse response) {
                callback.onGetTodoList(response.getCount(), response.getResults());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void addTodo(String content, AddTodoCallback callback) {
        mTodoService.addTodo(content, new BaseTodoServiceCallBack<AddTodoResponse>(AddTodoResponse.class) {

            @Override
            public void onSuccess(AddTodoResponse response) {
                callback.onAddTodo();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void deleteTodo(String id, DeleteTodoCallback callback) {

    }
}
