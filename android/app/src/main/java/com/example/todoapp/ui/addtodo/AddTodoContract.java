package com.example.todoapp.ui.addtodo;

import com.example.todoapp.ui.base.BasePresenter;
import com.example.todoapp.ui.base.BaseView;

public interface AddTodoContract {
    interface View extends BaseView {

        void showEmptyMessage();

        void finish(int resultCode);

        void showSuccessMessage();

        void finishActivityResultOk();
    }

    interface Presenter extends BasePresenter {

        void save(String content);
    }
}
