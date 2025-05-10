package com.ebook.np;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.np.api.Api;
import com.ebook.np.api.ApiService;
import com.ebook.np.dto.Response.BookResponse;
import com.ebook.np.model.Books;
import com.ebook.np.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BookAdapter adapter;
    private ArrayList<Books> bookList = new ArrayList<>();
    private EditText searchEditText;
    private AppCompatButton searchButton;
    private RecyclerView searchRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEditText = findViewById(R.id.editTextSearch);
        searchButton = findViewById(R.id.buttonSearch);
        searchRecyclerView = findViewById(R.id.recyclerViewResults);
        adapter = new BookAdapter(bookList , this);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        searchRecyclerView.setAdapter(adapter);
        toolbar();

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchEditText.getText().toString().trim();
            if (!searchTerm.isEmpty()) {
                searchBooks(searchTerm);
            }
        });

    }

    private void searchBooks(String searchTerm){

        ApiService apiService = Api.getClientAuth(this).create(ApiService.class);

        Call<BookResponse> call = apiService.searchBooks(searchTerm);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookResponse bookResponse = response.body();

                    if (bookResponse.getData() != null) {
                        // Clear existing data and add new data
                        bookList.clear();
                        bookList.addAll(bookResponse.getData());

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

    private  void toolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Books");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}