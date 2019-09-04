package com.example.todoapp.ui.addtodo;

import android.app.Activity;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.todo.TodoDataSource;
import com.example.todoapp.data.repository.todo.TodoRepository;

import javax.inject.Inject;

public class AddTodoPresenter implements AddTodoContract.Presenter {

    private final AddTodoContract.View mView;
    private final TodoRepository mTodoRepository;

    @Inject
    public AddTodoPresenter(AddTodoContract.View view, TodoRepository todoRepository) {
        mView = view;
        mTodoRepository = todoRepository;
    }

    @Override
    public void save(String content) {
        if (content.isEmpty()) {
            mView.showEmptyMessage();
            return;
        }


        mView.showLoadingView();
        mTodoRepository.addTodo(content, new TodoDataSource.AddTodoCallback() {
            @Override
            public void onAddTodo() {
                mView.dismissLoadingView();
                mView.showSuccessMessage();
                mView.finishActivityResultOk();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoServiceError(throwable, errorResponse);
            }
        });
    }
}
