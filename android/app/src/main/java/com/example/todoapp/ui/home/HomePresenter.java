package com.example.todoapp.ui.home;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.todo.TodoDataSource;
import com.example.todoapp.data.repository.todo.TodoRepository;

import java.util.List;

import javax.inject.Inject;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private final TodoRepository mTodoRepository;

    @Inject
    public HomePresenter(HomeContract.View view, TodoRepository todoRepository) {
        mView = view;
        mTodoRepository = todoRepository;
    }

    @Override
    public void getTodoList() {
        mView.showLoadingTodoListView();
        mTodoRepository.getTodoList(1, new TodoDataSource.GetTodoListCallback() {
            @Override
            public void onGetTodoList(int count, List<Todo> todoList) {
                mView.dismissLoadingTodoListView();

                mTodoRepository.setTodoList(todoList);

                if (mTodoRepository.getTodoList().isEmpty()) {
                    mView.showEmptyTodoList();
                } else {
                    mView.showTodoList(mTodoRepository.getTodoList());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoServiceError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void onAddButtonClick() {
        mView.openAddTodoActivity();
    }

    @Override
    public void deleteTodo(Todo todo) {
        mView.showLoadingView();
        mTodoRepository.deleteTodo(todo.getId(), new TodoDataSource.DeleteTodoCallback() {
            @Override
            public void onDeleteTodo() {
                mView.dismissLoadingView();
                getTodoList();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoServiceError(throwable, errorResponse);
            }
        });
    }
}
