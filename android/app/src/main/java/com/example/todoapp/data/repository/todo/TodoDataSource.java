package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.IDataSourceErrorCallback;

import java.util.List;

public interface TodoDataSource {

    void setTodoList(List<Todo> todoList);

    List<Todo> getTodoList();

    interface GetTodoListCallback extends IDataSourceErrorCallback {
        void onGetTodoList(int count, List<Todo> list);
    }

    void getTodoList(int page, GetTodoListCallback callback);

    interface AddTodoCallback extends IDataSourceErrorCallback{
        void onAddTodo();
    }

    void addTodo(String content, AddTodoCallback callback);


    interface DeleteTodoCallback extends IDataSourceErrorCallback{
        void onDeleteTodo();
    }

    void deleteTodo(String id, DeleteTodoCallback callback);
}
