package com.example.todoapp.ui.addtodo;

public interface AddTodoContract {
    interface View {

        void showEmptyMessage();

        void finish(int resultCode);
    }

    interface Presenter {

        void save(String title, String content);
    }
}
