package com.example.todoapp.data.network.todoapp.request;

public class SignUpRequest {
    private String ts;
    private String otp_id;
    private String otp;
    private String email;
    private String password;
    private String user_sign_pub_key;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public void setOtp_id(String otp_id) {
        this.otp_id = otp_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_sign_pub_key() {
        return user_sign_pub_key;
    }

    public void setUser_sign_pub_key(String user_sign_pub_key) {
        this.user_sign_pub_key = user_sign_pub_key;
    }
}
