package com.example.todoapp.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;
import com.example.todoapp.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity<SignUpViewModel> {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;

    @BindView(R.id.etOtp)
    EditText etOtp;

    SignUpViewModel mSignUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.sign_up_activity);
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mSignUpViewModel = obtainViewModel(SignUpViewModel.class);

        observeBaseLiveData(mSignUpViewModel);

        mSignUpViewModel.showSendOtpMessage().observe(this, show -> {
            if (show) {
                Toast.makeText(this, R.string.otp_send, Toast.LENGTH_SHORT).show();
            }
        });

        mSignUpViewModel.isSignUpSuccess().observe(this, isSignUpSuccess -> {
            if (isSignUpSuccess) {
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            }
        });
    }

    @OnClick(R.id.btnSignUp)
    public void signUp() {
        mSignUpViewModel.signUp(
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etPasswordConfirm.getText().toString(),
                etOtp.getText().toString()
        );
    }

    @OnClick(R.id.btnSendOtp)
    public void sendOtp() {
        mSignUpViewModel.sendOtp(etEmail.getText().toString());
    }
}
