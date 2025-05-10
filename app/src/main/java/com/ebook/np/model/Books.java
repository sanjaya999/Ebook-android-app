package com.ebook.np.model;


import com.google.gson.annotations.SerializedName;

public class Books {
    @SerializedName("_id")
    private String id;

    @SerializedName("bookName")
    private String bookName;

    @SerializedName("description")
    private String description;

    @SerializedName("bookImage")
    private String bookImage; // URL as String

    @SerializedName("bookFile")
    private String bookFile; // URL as String

    @SerializedName("uploadedBy")
    private String uploadedBy; // Assuming this is an ID String

    @SerializedName("accessCount")
    private int accessCount;

    @SerializedName("genre")
    private String genre;

    @SerializedName("createdAt")
    private String createdAt; // Keep as String, parse later if needed

    @SerializedName("updatedAt")
    private String updatedAt; // Keep as String, parse later if needed

    // We can ignore __v if not needed

    @SerializedName("approved")
    private boolean approved;

    // --- Getters for all fields ---

    public String getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getDescription() {
        return description;
    }

    public String getBookImage() {
        return bookImage;
    }

    public String getBookFile() {
        return bookFile;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public String getGenre() {
        return genre;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isApproved() {
        return approved;
    }
    public Books(String id, String bookName, String description, String bookImage, String bookFile) {
        this.id = id;
        this.bookName = bookName;
        this.description = description;
        this.bookImage = bookImage;
        this.bookFile = bookFile;
    }


}
