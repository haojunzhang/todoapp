package com.example.todoapp.ui.addtodo;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddTodoActivity extends BaseActivity implements AddTodoContract.View {

    @Inject
    AddTodoContract.Presenter mPresenter;

    @BindView(R.id.etContent)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_todo_activity);
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btnSave)
    public void onSaveClick(View view) {
        mPresenter.save(etContent.getText().toString());
    }

    @Override
    public void showEmptyMessage() {
        toast(R.string.cant_be_empty);
    }

    @Override
    public void finish(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override
    public void showSuccessMessage() {
        toast(R.string.success);
    }

    @Override
    public void finishActivityResultOk() {
        setResult(RESULT_OK);
        finish();
    }
}
