package com.example.todoapp.ui.resetpassword;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity<ResetPasswordViewModel> {

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;

    @BindView(R.id.etOtp)
    EditText etOtp;

    ResetPasswordViewModel mResetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.reset_password_activity);
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mResetPasswordViewModel = obtainViewModel(ResetPasswordViewModel.class);
        observeBaseLiveData(mResetPasswordViewModel);

        mResetPasswordViewModel.start(getIntent().getStringExtra("otpId"));

        mResetPasswordViewModel.isResetPasswordSuccess().observe(this, isResetPasswordSuccess -> {
            if (isResetPasswordSuccess) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @OnClick(R.id.btnResetPassword)
    public void signUp() {
        mResetPasswordViewModel.resetPassword(
                etPassword.getText().toString(),
                etPasswordConfirm.getText().toString(),
                etOtp.getText().toString()
        );
    }
}
