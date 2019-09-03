package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.IDataSourceErrorCallback;

import java.util.List;

public interface TodoDataSource {

    void setTodoList(List<Todo> todoList);

    List<Todo> getTodoList();

    interface GetTodoListCallback extends IDataSourceErrorCallback {
        void onGetTodoList(List<Todo> list);
    }

    void getTodoList(int page, GetTodoListCallback callback);

    interface AddTodoCallback {
        void onAddTodo(boolean success, Todo todo);
    }

    void addTodo(Todo todo, AddTodoCallback callback);


    interface DeleteTodoCallback {
        void onDeleteTodo(boolean success);
    }

    void deleteTodo(String id, DeleteTodoCallback callback);
}
