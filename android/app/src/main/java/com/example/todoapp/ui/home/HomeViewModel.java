package com.example.todoapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.todo.TodoDataSource;
import com.example.todoapp.data.repository.todo.TodoRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;

import java.util.List;

public class HomeViewModel extends BaseViewModel {

    private final TodoRepository mTodoRepository;

    private final MutableLiveData<Boolean> mIsLoadingTodoList = new MutableLiveData<>();
    private final MutableLiveData<List<Todo>> mTodoList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsClearTodoList = new MutableLiveData<>();

    private int page;
    private int maxPage;

    public HomeViewModel(TodoRepository mTodoRepository) {
        this.mTodoRepository = mTodoRepository;
    }

    public LiveData<Boolean> isLoadingTodoList() {
        return mIsLoadingTodoList;
    }

    public LiveData<List<Todo>> getTodoList() {
        return mTodoList;
    }

    public LiveData<Boolean> isClearTodoList(){
        return mIsClearTodoList;
    }

    public void loadFirstPageTodoList() {
        mIsClearTodoList.setValue(true);
        mIsLoading.setValue(true);
        mTodoRepository.getTodoList(1, new TodoDataSource.GetTodoListCallback() {
            @Override
            public void onGetTodoList(int count, List<Todo> todoList) {
                mIsLoading.setValue(false);

                // set page
                if (count % TodoRepository.PAGE_SIZE_LOAD_AT_ONCE == 0) {
                    maxPage = count / TodoRepository.PAGE_SIZE_LOAD_AT_ONCE;
                } else {
                    maxPage = (count / TodoRepository.PAGE_SIZE_LOAD_AT_ONCE) + 1;
                }
                page = 1;

                // show ui
                mTodoList.setValue(todoList);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }

    public void loadTodoList() {
        if (!isAllowLoadMore()) {
            return;
        }

        mIsLoadingTodoList.setValue(true);
        mTodoRepository.getTodoList(page + 1, new TodoDataSource.GetTodoListCallback() {
            @Override
            public void onGetTodoList(int count, List<Todo> todoList) {
                mIsLoadingTodoList.setValue(false);

                page++;

                // show ui
                mTodoList.setValue(todoList);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoadingTodoList.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }

    private boolean isAllowLoadMore() {
        // first page not load || is last page
        if (page == 0 || page >= maxPage) {
            return false;
        }

        // is loading
        if (mIsLoadingTodoList.getValue() != null && mIsLoadingTodoList.getValue()) {
            return false;
        }

        return true;
    }

    public void deleteTodo(String id) {
        mIsLoading.setValue(true);
        mTodoRepository.deleteTodo(id, new TodoDataSource.DeleteTodoCallback() {
            @Override
            public void onDeleteTodo() {
                mIsLoading.setValue(false);
                loadFirstPageTodoList();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }
}
