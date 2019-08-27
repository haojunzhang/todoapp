package com.example.todoapp.ui.addtodo;

import android.app.Activity;

import com.example.todoapp.data.entity.Todo;
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
    public void save(String title, String content) {
        if (title.isEmpty() || content.isEmpty()) {
            mView.showEmptyMessage();
            return;
        }


        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setContent(content);
        mTodoRepository.addTodo(todo, (success, todo1) -> {
            if (success) {
                mView.finish(Activity.RESULT_OK);
            }
        });
    }
}
