package com.example.todoapp.ui.home;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;
import com.example.todoapp.data.entity.Todo;

import java.util.List;

public interface HomeContract {
    interface View extends BaseView {
        void showTodoList(List<Todo> list);

        void showEmptyTodoList();

        void openAddTodoActivity();

        void showDeleteSuccessMessage();

        void showDeleteFailMessage();
    }

    interface Presenter extends BasePresenter {
        void getTodoList();

        void onAddButtonClick();

        void deleteTodo(Todo todo);
    }
}
