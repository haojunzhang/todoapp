package com.example.todoapp.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.ui.addtodo.AddTodoActivity;
import com.example.todoapp.ui.base.BaseActivity;
import com.example.todoapp.ui.base.BaseRecyclerViewAdapter;
import com.example.todoapp.ui.base.BaseViewHolder;
import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.utils.LogUtils;
import com.example.todoapp.utils.NativeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    public static final int RC_ADD_TODO = 0;

    @Inject
    HomeContract.Presenter mPresenter;

    @BindView(R.id.rvTodo)
    RecyclerView rvTodo;

    @BindView(R.id.tvEmpty)
    View tvEmpty;

    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
        super.onCreate(savedInstanceState);

        LogUtils.d("" + NativeUtils.getString(0));

        rvTodo.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this, R.layout.todo_item);
        rvTodo.setAdapter(adapter);
        rvTodo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    btnAdd.hide();
                } else {
                    btnAdd.show();
                }
            }
        });

        mPresenter.getTodoList();

    }

    class TodoAdapter extends BaseRecyclerViewAdapter<Todo> {

        TodoAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(Context context, BaseViewHolder holder, Todo todo) {
            holder.setText(R.id.tvTitle, todo.getTitle())
                    .setText(R.id.tvContent, todo.getContent())
                    .setOnClickListener(R.id.btnDelete, view -> showDeleteDialog(todo));
        }
    }

    private void showDeleteDialog(Todo todo) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.delete_confirm)
                .setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
                    mPresenter.deleteTodo(todo);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showTodoList(List<Todo> list) {
        adapter.setData(list);

        rvTodo.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyTodoList() {
        adapter.setData(Collections.emptyList());

        rvTodo.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnAdd)
    public void onAddClick(View v) {
        mPresenter.onAddButtonClick();
    }

    @Override
    public void openAddTodoActivity() {
        startActivityForResult(new Intent(this, AddTodoActivity.class), RC_ADD_TODO);
    }

    @Override
    public void showDeleteSuccessMessage() {
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteFailMessage() {
        Toast.makeText(this, R.string.fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        mPresenter.getTodoList();
    }
}
