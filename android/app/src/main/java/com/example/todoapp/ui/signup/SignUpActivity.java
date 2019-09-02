package com.example.todoapp.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;
import com.example.todoapp.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpContract.View {

    @Inject
    SignUpContract.Presenter mPresenter;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;

    @BindView(R.id.etOtp)
    EditText etOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.sign_up_activity);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showSendOtpMessage() {
        Toast.makeText(this, R.string.otp_send, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void finishAllActivity() {
        finishAffinity();
    }

    @OnClick(R.id.btnSignUp)
    public void signUp() {
        mPresenter.signUp(
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etPasswordConfirm.getText().toString(),
                etOtp.getText().toString()
        );
    }

    @OnClick(R.id.btnSendOtp)
    public void sendOtp() {
        mPresenter.sendOtp(etEmail.getText().toString());
    }
}
