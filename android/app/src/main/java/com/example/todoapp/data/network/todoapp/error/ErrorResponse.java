package com.example.todoapp.data.network.todoapp.error;

public class ErrorResponse {
    private String errorBody;
    private int http_code;
    private ErrorResponseInfo error;

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public ErrorResponseInfo getError() {
        return error;
    }

    public void setError(ErrorResponseInfo error) {
        this.error = error;
    }

    public void setErrorBodyString(String errorBody) {
        this.errorBody=errorBody;
    }

    public String getErrorBodyString(){
        return errorBody;
    }
}
