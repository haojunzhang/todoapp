package com.example.todoapp.data.network.todoapp.response;

import com.example.todoapp.data.entity.Todo;

import java.util.List;

public class GetTodoListResponse {
    private int count;
    private List<Todo> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Todo> getResults() {
        return results;
    }

    public void setResults(List<Todo> results) {
        this.results = results;
    }
}
