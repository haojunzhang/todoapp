package com.example.todoapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.ui.base.BaseActivity;
import com.example.todoapp.ui.forgotpassword.ForgotPasswordActivity;
import com.example.todoapp.ui.main.MainActivity;
import com.example.todoapp.ui.signup.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        mPresenter.login(
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

    @Override
    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
