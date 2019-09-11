package com.example.todoapp.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    protected final MutableLiveData<String> mErrorCode = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public LiveData<String> getErrorCode() {
        return mErrorCode;
    }
}
