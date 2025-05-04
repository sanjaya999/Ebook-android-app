package com.ebook.np;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookVH extends RecyclerView.ViewHolder {
    ImageView bookImage;
    TextView bookName , bookDescription , bookFile;

    public BookVH(@NonNull View view){
        super(view);

        bookImage = view.findViewById(R.id.bookImage);
        bookName = view.findViewById(R.id.bookName);
        bookDescription = view.findViewById(R.id.description);
        bookFile = view.findViewById(R.id.bookFile);


    }
}
