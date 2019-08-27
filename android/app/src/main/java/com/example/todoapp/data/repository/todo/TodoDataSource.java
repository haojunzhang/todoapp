package com.example.todoapp.data.repository.todo;

import com.example.todoapp.data.entity.Todo;

import java.util.List;

public interface TodoDataSource {

    // Get todo list
    interface GetTodoListCallback {
        void onGetTodoList(List<Todo> list);
    }

    void getTodoList(GetTodoListCallback callback);

    // Add todo

    interface AddTodoCallback {
        void onAddTodo(boolean success, Todo todo);
    }

    void addTodo(Todo todo, AddTodoCallback callback);


    // delete todo
    interface DeleteTodoCallback{
        void onDeleteTodo(boolean success);
    }

    void deleteTodo(String id, DeleteTodoCallback callback);
}
