package com.example.todoapp.data.network.todoapp;

import com.example.todoapp.data.network.todoapp.request.ChangePasswordRequest;
import com.example.todoapp.data.network.todoapp.request.LoginRequest;
import com.example.todoapp.data.network.todoapp.request.ResetPasswordRequest;
import com.example.todoapp.data.network.todoapp.request.SendOtpRequest;
import com.example.todoapp.data.network.todoapp.request.SignUpRequest;
import com.example.todoapp.data.network.todoapp.request.VerifyEmailRequest;

import java.lang.ref.Reference;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITodoService {

    @POST("users/")
    Call<String> signUp(@HeaderMap Map<String, String> headers,
                        @Body SignUpRequest request);

    @POST("users/login/")
    Call<String> login(@HeaderMap Map<String, String> headers,
                       @Body LoginRequest request);

    @DELETE("users/user_token/")
    Call<String> logout(@HeaderMap Map<String, String> headers,
                        @Query("ts") String ts);

    @POST("users/email/")
    Call<String> verifyEmail(@HeaderMap Map<String, String> headers,
                             @Body VerifyEmailRequest request);

    @POST("otps/")
    Call<String> sendOtp(@HeaderMap Map<String, String> headers,
                         @Body SendOtpRequest request);

    @PUT("users/{userId}/password/")
    Call<String> changePassword(@HeaderMap Map<String, String> headers,
                                @Path("userId") String userId,
                                @Body ChangePasswordRequest request);

    @PUT("users/reset_password/")
    Call<String> resetPassword(@HeaderMap Map<String, String> headers,
                               @Body ResetPasswordRequest request);

    @GET("users/{userId}/")
    Call<String> getUserProfile(@HeaderMap Map<String, String> headers,
                                @Path("userId") String userId,
                                @Query("ts") String ts);

    @GET("todos/")
    Call<String> getTodoList(@HeaderMap Map<String, String> headers,
                             @Query("page") String page,
                             @Query("page_size") String pageSize,
                             @Query("ts") String ts);
}
