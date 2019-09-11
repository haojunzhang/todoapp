package com.example.todoapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.base.BaseActivity;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordActivity;
import com.example.todoapp.ui.main.MainActivity;
import com.example.todoapp.ui.signup.SignUpActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginViewModel>{

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    private void initViewModel() {
        mLoginViewModel = obtainViewModel(LoginViewModel.class);

        observeBaseLiveData(mLoginViewModel);

        mLoginViewModel.isLoginSuccess().observe(this, isLoginSuccess -> {
            if (isLoginSuccess) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        mLoginViewModel.login(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        );
    }

    @OnClick(R.id.btnSignUp)
    public void openSignUpActivity() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.btnForgotPassword)
    public void openForgotPasswordActivity() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}
