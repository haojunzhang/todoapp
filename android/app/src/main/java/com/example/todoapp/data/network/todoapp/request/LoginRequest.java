package com.example.todoapp.data.network.todoapp.request;

public class LoginRequest {
    private String uts;
    private String phone;
    private String password;
    private String sign_public_key;

    public String getUts() {
        return uts;
    }

    public void setUts(String uts) {
        this.uts = uts;
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
