package com.example.todoapp.ui.resetpassword;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordContract.View {

    @Inject
    ResetPasswordContract.Presenter mPresenter;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;

    @BindView(R.id.etOtp)
    EditText etOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.reset_password_activity);
        super.onCreate(savedInstanceState);

        mPresenter.setExtra(getIntent().getStringExtra("otpId"));

    }

    @OnClick(R.id.btnResetPassword)
    public void signUp() {
        mPresenter.resetPassword(
                etPassword.getText().toString(),
                etPasswordConfirm.getText().toString(),
                etOtp.getText().toString()
        );
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
    }
}
