package com.example.todoapp.ui.addtodo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.todo.TodoDataSource;
import com.example.todoapp.data.repository.todo.TodoRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;

public class AddTodoViewModel extends BaseViewModel {

    private final TodoRepository mTodoRepository;

    private final MutableLiveData<Boolean> mShowEmptyMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsAddTodoSuccess = new MutableLiveData<>();

    public AddTodoViewModel(TodoRepository mTodoRepository) {
        this.mTodoRepository = mTodoRepository;
    }

    public LiveData<Boolean> showEmptyMessage() {
        return mShowEmptyMessage;
    }

    public LiveData<Boolean> isAddTodoSuccess() {
        return mIsAddTodoSuccess;
    }

    public void save(String content) {
        if (content.isEmpty()) {
            mShowEmptyMessage.setValue(true);
            return;
        }

        mIsLoading.setValue(true);
        mTodoRepository.addTodo(content, new TodoDataSource.AddTodoCallback() {
            @Override
            public void onAddTodo() {
                mIsLoading.setValue(false);
                mIsAddTodoSuccess.setValue(true);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }
}
