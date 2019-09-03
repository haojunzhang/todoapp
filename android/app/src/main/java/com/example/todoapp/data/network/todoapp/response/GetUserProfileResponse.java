package com.example.todoapp.data.network.todoapp.response;

public class GetUserProfileResponse {
    private String user_id;
    private String email;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
