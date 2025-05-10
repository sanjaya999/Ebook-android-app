package com.ebook.np;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.np.api.Api;
import com.ebook.np.api.ApiService;
import com.ebook.np.db.Bookdb;
import com.ebook.np.dto.Response.BookResponse;
import com.ebook.np.model.Books;
import com.ebook.np.session.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRecyclerViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ArrayList<Books> bookList = new ArrayList<>();
    private BookResponse bookResponse;
    private ArrayList<Books> bookResultList;
    private SessionManager sessionManager;
    private TabLayout tabLayout;
    private String profileImageUrl;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_recycler_view);
        sessionManager = new SessionManager(this);
        profileImageUrl = sessionManager.getUserProfileUrl();
        bookAdapter = new BookAdapter(bookList, this);
        toolbar();
        getBooks();


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findView();
            }
        },5000);

    }

    private void findView(){
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BookAdapter(bookList , BookRecyclerViewActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BookRecyclerViewActivity.this , 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);



    }

    private void getBookList(){

        ApiService apiService = Api.getClientAuth(this).create(ApiService.class);

        Call<BookResponse> call = apiService.getallBooks();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookResponse bookResponse = response.body();

                    if (bookResponse.getData() != null) {
                        // Clear existing data and add new data
                        bookList.clear();
                        bookList.addAll(bookResponse.getData());

                        Bookdb bookdb = new Bookdb(BookRecyclerViewActivity.this);
                        for (Books book : bookResponse.getData()) {
                            bookdb.insertBook(book);
                        }
                        // Notify adapter that data has changed
                        adapter.notifyDataSetChanged();

                    } else {
                        showError("Failed to load books: " + response.code());
                    }
                } else {
                    showError("Failed to load books: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                showError("Network error: " + t.getMessage());

            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private  void toolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Books");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile){
            Intent i = new Intent(BookRecyclerViewActivity.this , ProfileActivity.class);
            startActivity(i);
            Toast.makeText(this , "profile" , Toast.LENGTH_SHORT).show();
        } else if (id == R.id.logout) {
            sessionManager.logoutUser();
            Intent i = new Intent(BookRecyclerViewActivity.this , LoginActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.search) {
            Intent i = new Intent(BookRecyclerViewActivity.this , SearchActivity.class);
            startActivity(i);
            Toast.makeText(this , "search" , Toast.LENGTH_SHORT).show();
        } else if (id == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }



    private void getBooks() {
        // Create a Bookdb instance to access local data
        Bookdb bookdb = new Bookdb(BookRecyclerViewActivity.this);

        // Fetch books from local database
        ArrayList<Books> localBooks = bookdb.getAllBooks();

        // If books are available in the local database, display them
        if (localBooks != null && !localBooks.isEmpty()) {
            bookList.clear();
            bookList.addAll(localBooks);
        } else {
            getBookList();
        }
    }

}