package com.example.todoapp.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment implements BaseView{

    protected abstract int getLayoutId();

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    public void handleTodoServiceError(Throwable throwable, ErrorResponse errorResponse) {
        if (getBaseActivity() != null) {
            getBaseActivity().handleTodoServiceError(throwable, errorResponse);
        }
    }

    public void showLoadingView() {
        if (getBaseActivity() != null) {
            getBaseActivity().showLoadingView();
        }
    }

    public void dismissLoadingView() {
        if (getBaseActivity() != null) {
            getBaseActivity().dismissLoadingView();
        }
    }

    public void finishActivity() {
        if (getBaseActivity() != null) {
            getBaseActivity().finish();
        }
    }

    public void toast(int resId) {
        if (getBaseActivity() != null) {
            getBaseActivity().toast(resId);
        }
    }

    public void logout(){
        if (getBaseActivity() != null) {
            getBaseActivity().logout();
        }
    }
}
