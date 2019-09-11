package com.example.todoapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.todoapp.R;
import com.example.todoapp.mvvm.ViewModelFactory;
import com.example.todoapp.utils.LogoutUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class BaseActivity<T extends ViewModel> extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    LogoutUtils mLogoutUtils;

    @Inject
    ViewModelFactory mViewModelFactory;

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

    protected T obtainViewModel(Class<T> clazz) {
        return ViewModelProviders.of(this, mViewModelFactory).get(clazz);
    }

    protected void observeBaseLiveData(BaseViewModel mBaseViewModel){
        // is loading
        mBaseViewModel.isLoading().observe(this, isLoading ->{
            if (isLoading) {
                showLoadingView();
            } else {
                dismissLoadingView();
            }
        });

        // error message
        mBaseViewModel.getErrorCode().observe(this, errorMessage -> {
            if (errorMessage != null) {
                alert(errorMessage);
            }
        });
    }

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

    public void dismissLoadingView() {
        if (loadingDialog != null && loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.dismiss();
        }
    }

    public void handleTodoServiceError(String code) {
        alert(getErrorMessageFromErrorCode(code));
    }

    protected String getErrorMessageFromErrorCode(String code){
        //
        return code;
    }

    protected void alert(String text) {
        new AlertDialog.Builder(this)
                .setMessage(text)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }

    protected void toast(int resId) {
        toast(getString(resId));
    }

    protected void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        mLogoutUtils.logout();
    }
}
