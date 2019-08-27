package com.example.todoapp.ui.home;

import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.data.repository.todo.TodoRepository;

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
        mTodoRepository.getTodoList(list -> {
            if (list.isEmpty()) {
                mView.showEmptyTodoList();
            } else {
                mView.showTodoList(list);
            }
        });
    }

    @Override
    public void onAddButtonClick() {
        mView.openAddTodoActivity();
    }

    @Override
    public void deleteTodo(Todo todo) {
        mTodoRepository.deleteTodo(todo.getId(), success -> {
            if (success){
                mView.showDeleteSuccessMessage();
                getTodoList();
            }else{
                mView.showDeleteFailMessage();
            }
        });
    }
}
