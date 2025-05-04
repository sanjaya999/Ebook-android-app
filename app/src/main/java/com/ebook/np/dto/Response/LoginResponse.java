package com.ebook.np.dto.Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private LoginData data;

    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public LoginData getData() { return data; }
}
