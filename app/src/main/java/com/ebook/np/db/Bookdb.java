package com.ebook.np.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ebook.np.model.Books;

import java.util.ArrayList;

public class Bookdb {
    private Database database;

    public Bookdb(Context context) {
        database = new Database(context);
    }

    // Method to insert book data into the database
    public void insertBook(Books book) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(database.COLUMN_ID, book.getId());
        values.put(database.COLUMN_BOOK_NAME, book.getBookName());
        values.put(database.COLUMN_DESCRIPTION, book.getDescription());
        values.put(database.COLUMN_BOOK_IMAGE, book.getBookImage());
        values.put(database.COLUMN_BOOK_FILE, book.getBookFile());

        db.insert(database.TABLE_BOOKS, null, values);
        db.close();
    }

    public ArrayList<Books> getAllBooks() {
        ArrayList<Books> bookList = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();

        // Query to retrieve all books from the database
        String query = "SELECT * FROM " + database.TABLE_BOOKS;
        Cursor cursor = db.rawQuery(query, null);

        // Check if there are any books and retrieve them
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(database.COLUMN_ID));
                String bookName = cursor.getString(cursor.getColumnIndex(database.COLUMN_BOOK_NAME));
                String description = cursor.getString(cursor.getColumnIndex(database.COLUMN_DESCRIPTION));
                String bookImage = cursor.getString(cursor.getColumnIndex(database.COLUMN_BOOK_IMAGE));
                String bookFile = cursor.getString(cursor.getColumnIndex(database.COLUMN_BOOK_FILE));

                // Create a new Books object and add it to the list
                Books book = new Books(id, bookName, description, bookImage, bookFile);
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bookList;
    }
}
