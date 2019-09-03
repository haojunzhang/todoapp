package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.entity.UserProfile;
import com.example.todoapp.data.network.todoapp.BaseTodoServiceCallBack;
import com.example.todoapp.data.network.todoapp.response.ChangePasswordResponse;
import com.example.todoapp.data.network.todoapp.response.GetUserProfileResponse;
import com.example.todoapp.data.network.todoapp.response.LoginResponse;
import com.example.todoapp.data.network.todoapp.response.LogoutResponse;
import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.response.ResetPasswordResponse;
import com.example.todoapp.data.network.todoapp.response.SendOtpResponse;
import com.example.todoapp.data.network.todoapp.response.SignUpResponse;
import com.example.todoapp.data.network.todoapp.response.VerifyEmailResponse;

public class UserRemoteDataSource implements UserDataSource {

    private final TodoService mTodoService;

    public UserRemoteDataSource(TodoService mTodoService) {
        this.mTodoService = mTodoService;
    }

    @Override
    public void setUserId(String userId) {
        // do nothing
    }

    @Override
    public String getUserId() {
        // do nothing
        return null;
    }

    @Override
    public void setUserToken(String userToken) {
        // do nothing
    }

    @Override
    public String getUserToken() {
        // do nothing
        return null;
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
// do nothing
    }

    @Override
    public UserProfile getUserProfile() {
        // do nothing
        return null;
    }

    @Override
    public void logout() {
        mTodoService.logout(new BaseTodoServiceCallBack<LogoutResponse>(LogoutResponse.class) {
            @Override
            public void onSuccess(LogoutResponse response) {

            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {

            }
        });
    }

    @Override
    public void login(String phone, String password, LoginCallback callback) {
        mTodoService.login(phone, password, new BaseTodoServiceCallBack<LoginResponse>(LoginResponse.class) {
            @Override
            public void onSuccess(LoginResponse response) {
                callback.onLogin(response.getUser_id(), response.getUser_token());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void signUp(String email, String password, String otpId, String otp, SignUpCallback callback) {
        mTodoService.signUp(email, password, otpId, otp, new BaseTodoServiceCallBack<SignUpResponse>(SignUpResponse.class) {
            @Override
            public void onSuccess(SignUpResponse response) {
                callback.onSignUp(response.getUser_id(), response.getUser_token());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void verifyEmail(String email, VerifyEmailCallback callback) {
        mTodoService.verifyEmail(email, new BaseTodoServiceCallBack<VerifyEmailResponse>(VerifyEmailResponse.class) {
            @Override
            public void onSuccess(VerifyEmailResponse response) {
                callback.onVerifyEmail(response.getOtp_id());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void sendOtp(String email, SendOtpCallback callback) {
        mTodoService.sendOtp(email, new BaseTodoServiceCallBack<SendOtpResponse>(SendOtpResponse.class) {
            @Override
            public void onSuccess(SendOtpResponse response) {
                callback.onSendOtp(response.getOtp_id());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback) {
        mTodoService.changePassword(password, otpId, otp, new BaseTodoServiceCallBack<ChangePasswordResponse>(ChangePasswordResponse.class) {
            @Override
            public void onSuccess(ChangePasswordResponse response) {
                callback.onChangePassword(response.getUser_token());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback) {
        mTodoService.resetPassword(password, otpId, otp, new BaseTodoServiceCallBack<ResetPasswordResponse>(ResetPasswordResponse.class) {
            @Override
            public void onSuccess(ResetPasswordResponse response) {
                callback.onResetPassword();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void getUserProfile(GetProfileCallback callback) {
        mTodoService.getUserProfile(new BaseTodoServiceCallBack<GetUserProfileResponse>(GetUserProfileResponse.class){

            @Override
            public void onSuccess(GetUserProfileResponse response) {
                callback.onGetProfile(response.getUser_id(), response.getEmail());
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {

            }
        });
    }

    @Override
    public void resendOTPCode(String otpId, ResendOTPCodeCallback callback) {

    }
}
