package com.example.todoapp.data.network.todoapp.request;

public class LoginRequest {
    private String ts;
    private String email;
    private String password;
    private String user_sign_pub_key;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
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
