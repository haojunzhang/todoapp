package com.example.todoapp.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;
import com.example.todoapp.ui.resetpassword.ResetPasswordActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View {

    @Inject
    ForgotPasswordContract.Presenter mPresenter;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.forgot_password_activity);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btnForgotPassword)
    public void forgotPassword() {
        mPresenter.forgotPassword(etEmail.getText().toString());
    }

    @Override
    public void openResetPasswordActivity(String otpId) {
        startActivity(new Intent(this, ResetPasswordActivity.class)
                .putExtra("otpId", otpId));
    }
}
