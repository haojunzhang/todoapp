package com.example.todoapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.R;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.utils.ErrorCodeUtils;
import com.example.todoapp.utils.LogUtils;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // view binding
        ButterKnife.bind(this);

        // DI inject
        AndroidInjection.inject(this);
    }

    @Override
    public void showLoadingView() {
        if (loadingDialog == null) {
            loadingDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(LayoutInflater.from(this).inflate(R.layout.loading_dialog, null))
                    .create();
        }
        if (!isFinishing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingView() {
        if (loadingDialog != null && loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void handleTodoPocketServiceError(Throwable throwable, ErrorResponse errorResponse) {
        dismissLoadingView();
        String code = ErrorCodeUtils.getCode(throwable, errorResponse);
        String message = ErrorCodeUtils.getMessage(this, code, throwable, errorResponse);
        alert(message);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    protected void alert(String text) {
        new AlertDialog.Builder(this)
                .setMessage(text)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }
}
