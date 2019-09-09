package com.example.todoapp.data.entity;

public class Todo {
    private String todo_id;
    private String content;

    public String getTodo_Id() {
        return todo_id;
    }

    public void setTodo_Id(String id) {
        this.todo_id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
