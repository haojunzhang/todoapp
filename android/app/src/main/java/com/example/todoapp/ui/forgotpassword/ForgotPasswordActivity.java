package com.example.todoapp.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;
import com.example.todoapp.ui.resetpassword.ResetPasswordActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordViewModel> {

    @BindView(R.id.etEmail)
    EditText etEmail;

    ForgotPasswordViewModel mForgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.forgot_password_activity);
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mForgotPasswordViewModel = obtainViewModel(ForgotPasswordViewModel.class);

        observeBaseLiveData(mForgotPasswordViewModel);

        mForgotPasswordViewModel.getOtpId().observe(this, otpId -> {
            if (otpId != null) {
                startActivity(new Intent(this, ResetPasswordActivity.class)
                        .putExtra("otpId", otpId));
            }
        });
    }

    @OnClick(R.id.btnForgotPassword)
    public void forgotPassword() {
        mForgotPasswordViewModel.forgotPassword(etEmail.getText().toString());
    }
}
