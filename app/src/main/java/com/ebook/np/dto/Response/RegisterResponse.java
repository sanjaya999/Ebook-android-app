package com.ebook.np.dto.Response;

import java.util.List;

public class RegisterResponse {
    private int statusCode;
    private Data data;
    private String message;
    private boolean success;

    // Getters and Setters
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public static class Data {
        private String _id;
        private String fullName;
        private String email;
        private boolean isAdmin;
        private boolean isApproved;
        private boolean newUser;
        private String createdAt;
        private String updatedAt;
        private int __v;

        // Assuming genres and bookmarks are arrays
        private List<String> genres;
        private List<String> bookmarks;

        // Getters and Setters
        public String get_id() { return _id; }
        public void set_id(String _id) { this._id = _id; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public boolean isAdmin() { return isAdmin; }
        public void setAdmin(boolean admin) { isAdmin = admin; }

        public boolean isApproved() { return isApproved; }
        public void setApproved(boolean approved) { isApproved = approved; }

        public boolean isNewUser() { return newUser; }
        public void setNewUser(boolean newUser) { this.newUser = newUser; }

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

        public String getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

        public int get__v() { return __v; }
        public void set__v(int __v) { this.__v = __v; }

        public List<String> getGenres() { return genres; }
        public void setGenres(List<String> genres) { this.genres = genres; }

        public List<String> getBookmarks() { return bookmarks; }
        public void setBookmarks(List<String> bookmarks) { this.bookmarks = bookmarks; }
    }
}
