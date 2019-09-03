package com.example.todoapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.R;
import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.ui.addtodo.AddTodoActivity;
import com.example.todoapp.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    public static final int RC_ADD_TODO = 0;

    @Inject
    HomeContract.Presenter mPresenter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_frgament;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter.getTodoList();
    }

    @Override
    public void showTodoList(List<Todo> list) {

    }

    @Override
    public void showEmptyTodoList() {

    }

    @Override
    public void showDeleteSuccessMessage() {

    }

    @Override
    public void showDeleteFailMessage() {

    }

    @Override
    public void showLoadingTodoListView() {

    }

    @Override
    public void dismissLoadingTodoListView() {

    }

    @OnClick(R.id.btnAddTodo)
    public void onAddTodo() {
        startActivityForResult(new Intent(getActivity(), AddTodoActivity.class), RC_ADD_TODO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        mPresenter.getTodoList();
    }

}
