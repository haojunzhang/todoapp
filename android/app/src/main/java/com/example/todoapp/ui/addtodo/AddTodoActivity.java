package com.example.todoapp.ui.addtodo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTodoActivity extends BaseActivity<AddTodoViewModel> {

    @BindView(R.id.etContent)
    EditText etContent;

    AddTodoViewModel mAddTodoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_todo_activity);
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mAddTodoViewModel = obtainViewModel(AddTodoViewModel.class);

        observeBaseLiveData(mAddTodoViewModel);

        mAddTodoViewModel.showEmptyMessage().observe(this, show -> {
            if (show) {
                toast(R.string.cant_be_empty);
            }
        });

        mAddTodoViewModel.isAddTodoSuccess().observe(this, isAddTodoSuccess -> {
            if (isAddTodoSuccess) {
                toast(R.string.success);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void onSaveClick(View view) {
        mAddTodoViewModel.save(etContent.getText().toString());
    }
}
