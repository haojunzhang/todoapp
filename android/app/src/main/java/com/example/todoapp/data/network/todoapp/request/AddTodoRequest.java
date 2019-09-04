package com.example.todoapp.data.network.todoapp.request;

public class AddTodoRequest {
    private String ts;
    private String content;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
