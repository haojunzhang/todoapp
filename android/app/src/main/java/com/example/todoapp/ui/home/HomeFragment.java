package com.example.todoapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.ui.addtodo.AddTodoActivity;
import com.example.todoapp.ui.base.BaseFragment;
import com.example.todoapp.ui.base.BaseRecyclerViewAdapter;
import com.example.todoapp.ui.base.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    public static final int RC_ADD_TODO = 0;

    @Inject
    HomeContract.Presenter mPresenter;

    @BindView(R.id.rvTodo)
    RecyclerView rvTodo;

    private TodoAdapter adapter;

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

        rvTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTodo.setAdapter(adapter=new TodoAdapter(getActivity(), R.layout.todo_item));

        mPresenter.getTodoList();
    }

    class TodoAdapter extends BaseRecyclerViewAdapter<Todo> {

        TodoAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(Context context, BaseViewHolder holder, Todo todo) {

            // loading view
            if (todo == null) {
                holder.setVisible(R.id.layoutItem, View.GONE);
                holder.setVisible(R.id.progressBar, View.VISIBLE);
            } else {
                holder.setVisible(R.id.layoutItem, View.VISIBLE);
                holder.setVisible(R.id.progressBar, View.GONE);

                holder.setText(R.id.tvContent, todo.getContent());
                holder.setOnLongClickListener(R.id.layoutItem, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mPresenter.
                        return false;
                    }
                })
            }
        }
    }

    @Override
    public void showTodoList(List<Todo> list) {
        adapter.setData(list);
    }

    @Override
    public void showEmptyTodoList() {
        adapter.clear();
    }

    @Override
    public void showDeleteSuccessMessage() {
        toast(R.string.success);
    }

    @Override
    public void showDeleteFailMessage() {
        toast(R.string.fail);
    }

    @Override
    public void showLoadingTodoListView() {

    }

    @Override
    public void dismissLoadingTodoListView() {

    }

    @Override
    public void openAddTodoActivity() {
        startActivity(new Intent(getActivity(), AddTodoActivity.class));
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
