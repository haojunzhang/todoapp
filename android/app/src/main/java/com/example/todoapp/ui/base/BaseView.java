package com.example.todoapp.ui.base;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;

public interface BaseView {

    void showLoadingView();

    void dismissLoadingView();

    void handleTodoServiceError(Throwable throwable, ErrorResponse errorResponse);

    void finishActivity();
}
