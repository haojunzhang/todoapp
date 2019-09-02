package com.example.todoapp.data.network.todoapp.request;

public class SendOtpRequest {
    private String ts;
    private String email;

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
}
