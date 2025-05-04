package com.ebook.np.dto.Response;

import com.ebook.np.model.Books;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {
    @SerializedName("statusCode")
    private int statusCode;

    // The 'data' field in JSON is a list of Book objects
    @SerializedName("data")
    private List<Books> data;

    // --- Getters ---

    public int getStatusCode() {
        return statusCode;
    }

    public List<Books> getData() {
        return data;
    }
}
