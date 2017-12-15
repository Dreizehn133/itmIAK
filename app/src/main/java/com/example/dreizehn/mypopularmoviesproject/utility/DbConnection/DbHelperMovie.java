package com.example.dreizehn.mypopularmoviesproject.utility.DbConnection;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dreizehn on 12/8/2017.
 */

public class DbHelperMovie extends SQLiteOpenHelper {

    public static final short VERSION = 1;
    public static final String NAMA_DB = "movieDB.db";


    public DbHelperMovie(Context context) {
        super(context, NAMA_DB,null,VERSION);
    }

    public DbHelperMovie(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MOVIE ="CREATE TABLE "+ContractDbMovie.MovieList.NAMA_TABEL+" (" +
                ContractDbMovie.MovieList._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ContractDbMovie.MovieList.KOLOM_ID+" INTEGER NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_TITLE+" TEXT NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_RATE+" FLOAT NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_DATE+" TEXT NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_OVERVIEW+" TEXT NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_POSTER+" TEXT NOT NULL,"+
                ContractDbMovie.MovieList.KOLOM_BACKDROP+" TEXT NOT NULL"+
                ");";
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ContractDbMovie.MovieList.NAMA_TABEL);
        onCreate(db);
    }
}
