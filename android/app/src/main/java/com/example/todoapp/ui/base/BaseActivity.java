package com.example.todoapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.R;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class BaseActivity extends AppCompatActivity implements BaseView{

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
}
