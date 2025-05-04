package com.ebook.np.dto.Response;

import com.ebook.np.model.User;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("user")
    private User user;

    @SerializedName("accessToken")
    private String accessToken;

    public User getUser() { return user; }
    public String getAccessToken() { return accessToken; }
}
