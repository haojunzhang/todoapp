package com.example.todoapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.utils.ErrorCodeUtils;
import com.example.todoapp.utils.LogUtils;
import com.example.todoapp.utils.LogoutUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector,BaseView {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    LogoutUtils mLogoutUtils;

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
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
    public void handleTodoServiceError(Throwable throwable, ErrorResponse errorResponse) {
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

    protected void toast(int resId){
        toast(getString(resId));
    }

    protected void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        mLogoutUtils.logout();
    }
}
