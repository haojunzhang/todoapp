package com.example.todoapp.ui.setting;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseFragment;

public class SettingFragment extends BaseFragment {
    public static SettingFragment getInstance(){
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }
}
