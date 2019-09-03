package com.example.todoapp.data.network.todoapp;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;

public interface IDataSourceErrorCallback {
    void onError(Throwable throwable, ErrorResponse errorResponse);
}
