package com.example.todoapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseFragment;
import com.example.todoapp.databinding.SettingFragmentBinding;
import com.example.todoapp.ui.splash.SplashActivity;
import com.example.todoapp.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment<SettingViewModel, SettingFragmentBinding> {

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    SettingViewModel mSettingViewModel;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();

        mSettingViewModel.getUserProfile();
    }

    private void initViewModel() {
        mSettingViewModel = obtainViewModel(SettingViewModel.class);

        // ViewModel <> layout
        binding.setVm(mSettingViewModel);
        binding.setLifecycleOwner(getActivity());

        // base observe
        observeBaseLiveData(mSettingViewModel);

        mSettingViewModel.start();

        mSettingViewModel.getEmail().observe(this, email -> {
            LogUtils.d("fragment:" + email);
        });

        mSettingViewModel.isLogout().observe(this, isLogout -> {
            if (isLogout) {
                super.logout();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                finishActivity();
            }
        });
    }

    @OnClick(R.id.btnLogout)
    public void onLogoutClick() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.logout_confirm)
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    mSettingViewModel.logout();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
