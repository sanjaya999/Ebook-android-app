package com.ebook.np;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebook.np.model.Books;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookVH> {

    private ArrayList<Books> books= new ArrayList<>();
    private Context mContext;
    public BookAdapter(ArrayList<Books> dataList, Context context){
        this.books = dataList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_book_item , parent , false);
        return new BookVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookVH holder, int position) {
        holder.bookName.setText(books.get(position).getBookName());
        holder.bookDescription.setText(books.get(position).getDescription());
        holder.bookFile.setText(books.get(position).getBookFile());
        Picasso.get().load(books.get(position).getBookImage()).into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
