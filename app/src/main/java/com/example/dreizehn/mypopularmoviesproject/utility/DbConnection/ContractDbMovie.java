package com.example.dreizehn.mypopularmoviesproject.utility.DbConnection;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dreizehn on 12/8/2017.
 */

public class ContractDbMovie {
    //agar tdk dapat terjadi pembuatan
    private ContractDbMovie() {}

    public static final String AUTHORITY = "com.example.dreizehn.mypopularmoviesproject";
    public static final Uri BASE_CONTENT = Uri.parse("content://"+AUTHORITY);


    public static class MovieList implements BaseColumns{
        public static final String PATH_MOVIE="movie";
        public static final Uri CONTENT_URI=BASE_CONTENT.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String NAMA_TABEL = "movieList";

        //declarate field
        public static final String KOLOM_TITLE = "title";
        public static final String KOLOM_DATE = "date";
        public static final String KOLOM_ID = "idMovie";
        public static final String KOLOM_RATE = "rate";
        public static final String KOLOM_POSTER = "posterPath";
        public static final String KOLOM_BACKDROP = "backDropPath";
        public static final String KOLOM_OVERVIEW = "overView";


    }

}
