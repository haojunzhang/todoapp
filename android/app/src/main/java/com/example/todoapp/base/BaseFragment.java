package com.example.todoapp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.mvvm.ViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends ViewModel, U extends ViewDataBinding> extends Fragment {

    @Inject
    ViewModelFactory mViewModelFactory;

    protected U binding;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    protected T obtainViewModel(Class<T> clazz) {
        return ViewModelProviders.of(this, mViewModelFactory).get(clazz);
    }

    protected void observeBaseLiveData(BaseViewModel mBaseViewModel) {
        if (getBaseActivity() != null) {
            getBaseActivity().observeBaseLiveData(mBaseViewModel);
        }
    }

    private BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    public void handleTodoServiceError(String code) {
        if (getBaseActivity() != null) {
            getBaseActivity().handleTodoServiceError(code);
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

    public void logout() {
        if (getBaseActivity() != null) {
            getBaseActivity().logout();
        }
    }
}
