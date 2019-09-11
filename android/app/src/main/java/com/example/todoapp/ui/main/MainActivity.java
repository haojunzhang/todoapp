package com.example.todoapp.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;
import com.example.todoapp.base.EmptyViewModel;
import com.example.todoapp.ui.home.HomeFragment;
import com.example.todoapp.ui.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<EmptyViewModel> {

    @BindView(R.id.bottomNavView)
    BottomNavigationView bottomNavView;

    private HomeFragment mHomeFragment;
    private SettingFragment mSettingFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int selectedId;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (selectedId == item.getItemId()) {
                    return false;
                }
                selectedId = item.getItemId();
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        switchFragment(mHomeFragment);
                        break;
                    case R.id.btnSetting:
                        switchFragment(mSettingFragment);
                        break;
                }
                return false;
            }
        });

        initFragment();

        showDefaultFragment();
    }

    private void initFragment() {
        mHomeFragment = HomeFragment.getInstance();
        mSettingFragment = SettingFragment.getInstance();
    }

    private void showDefaultFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layoutContent, mHomeFragment)
                .commit();
        mCurrentFragment = mHomeFragment;
    }

    private void switchFragment(Fragment fragment) {

        if (fragment != mCurrentFragment) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .add(R.id.layoutContent, fragment)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .show(fragment)
                        .commit();
            }
            mCurrentFragment = fragment;
        }
    }


}
