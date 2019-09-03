package com.example.todoapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseFragment;
import com.example.todoapp.ui.splash.SplashActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment implements SettingContract.View {

    @Inject
    SettingContract.Presenter mPresenter;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

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
        mPresenter.showUI();
        mPresenter.getUserProfile();
    }

    @OnClick(R.id.btnLogout)
    public void onLogoutClick() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.logout_confirm)
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    mPresenter.logout();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void logout() {
        super.logout();
    }

    @Override
    public void openSplashActivity() {
        startActivity(new Intent(getActivity(), SplashActivity.class));
    }

    @Override
    public void showEmailText(String text) {
        tvEmail.setText(text);
    }
}
