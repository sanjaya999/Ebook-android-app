package com.ebook.np.api;

import com.ebook.np.dto.Request.LoginRequest;
import com.ebook.np.dto.Request.RegisterRequest;
import com.ebook.np.dto.Response.BookResponse;
import com.ebook.np.dto.Response.LoginResponse;
import com.ebook.np.dto.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/v1/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/v1/user/explore")
    Call<BookResponse> getallBooks();

    @POST("api/v1/user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @GET("api/v1/user/search")
    Call<BookResponse> searchBooks( @Query("searchTerm") String searchTerm);

}
