package com.example.todoapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.todoapp.R;
import com.example.todoapp.data.entity.Todo;
import com.example.todoapp.databinding.HomeFragmentBinding;
import com.example.todoapp.ui.addtodo.AddTodoActivity;
import com.example.todoapp.base.BaseFragment;
import com.example.todoapp.base.BaseRecyclerViewAdapter;
import com.example.todoapp.base.BaseViewHolder;
import com.example.todoapp.utils.LogUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomeViewModel, HomeFragmentBinding> {

    public static final int RC_ADD_TODO = 0;

    @BindView(R.id.rvTodo)
    RecyclerView rvTodo;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.btnAddTodo)
    FloatingActionButton btnAddTodo;


    HomeViewModel mHomeViewModel;

    private LinearLayoutManager layoutManager;
    private TodoAdapter adapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();

        rvTodo.setLayoutManager(layoutManager = new LinearLayoutManager(getActivity()));
        rvTodo.setAdapter(adapter = new TodoAdapter(getActivity(), R.layout.todo_item));
        rvTodo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == -1 || adapter.getData().size() == 0) {
                    return;
                }

                if (dy > 0) {
                    btnAddTodo.hide();
                } else {
                    btnAddTodo.show();
                }

                if (layoutManager.findLastVisibleItemPosition() == adapter.getData().size() - 1) {
                    mHomeViewModel.loadTodoList();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> mHomeViewModel.loadFirstPageTodoList());

        mHomeViewModel.loadFirstPageTodoList();
    }

    private void initViewModel() {
        mHomeViewModel = obtainViewModel(HomeViewModel.class);

        // ViewModel <> layout
        binding.setVm(mHomeViewModel);
        binding.setLifecycleOwner(getActivity());

        observeBaseLiveData(mHomeViewModel);

        mHomeViewModel.isLoadingTodoList().observe(this, isLoadingTodoList -> {
            if (isLoadingTodoList) {
                adapter.showLoading();
            } else {
                adapter.dismissLoading();
            }
        });

        mHomeViewModel.getTodoList().observe(this, todoList -> {
            adapter.addAll(todoList);
        });

        mHomeViewModel.isClearTodoList().observe(this, isClearTodoList -> {
            if (isClearTodoList) {
                adapter.clear();
            }
        });
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
                holder.setOnLongClickListener(R.id.layoutItem, view -> {
                    showDeleteAlert(todo.getTodo_Id());
                    return false;
                });
            }
        }
    }

    private void showDeleteAlert(String id) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.delete_confirm)
                .setPositiveButton(R.string.confirm, (dialogInterface, i) -> mHomeViewModel.deleteTodo(id))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @OnClick(R.id.btnAddTodo)
    public void onAddTodo() {
        startActivityForResult(new Intent(getActivity(), AddTodoActivity.class), RC_ADD_TODO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        mHomeViewModel.loadFirstPageTodoList();
    }

}
