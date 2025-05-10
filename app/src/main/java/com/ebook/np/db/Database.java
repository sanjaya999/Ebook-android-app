package com.ebook.np.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table and columns names
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BOOK_NAME = "bookName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_BOOK_IMAGE = "bookImage";
    public static final String COLUMN_BOOK_FILE = "bookFile";

    private static final String CREATE_TABLE_BOOKS = "CREATE TABLE " + TABLE_BOOKS + " ("
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_BOOK_NAME + " TEXT, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_BOOK_IMAGE + " TEXT, "
            + COLUMN_BOOK_FILE + " TEXT"
            + ")";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOKS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(sqLiteDatabase);
    }
}
