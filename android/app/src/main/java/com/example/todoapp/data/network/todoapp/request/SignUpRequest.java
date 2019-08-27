package com.example.todoapp.data.network.todoapp.request;

public class SignUpRequest {
    private String uts;
    private String otp_id;
    private String otp;
    private String phone;
    private String password;
    private String sign_public_key;

    public String getUts() {
        return uts;
    }

    public void setUts(String uts) {
        this.uts = uts;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign_public_key() {
        return sign_public_key;
    }

    public void setSign_public_key(String sign_public_key) {
        this.sign_public_key = sign_public_key;
    }
}
