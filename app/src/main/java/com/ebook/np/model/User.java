package com.ebook.np.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String _id;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("profile")
    private String profile;

    public String getId() { return _id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getProfile() { return profile; }}
