package com.ebook.np;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        holder.bookFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String pdfUrl = books.get(adapterPosition).getBookFile();
                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(pdfUrl));

                        // Add this to handle if no app can open the PDF
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try {
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            // Handle case where no app can open this type of file
                            e.printStackTrace();
                        }
                    }
                }
            }
        });        Picasso.get().load(books.get(position).getBookImage()).into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
