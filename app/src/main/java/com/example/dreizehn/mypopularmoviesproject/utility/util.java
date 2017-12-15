package com.example.dreizehn.mypopularmoviesproject.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dreizehn on 12/1/2017.
 */
public class util {
    //public static final String KEY = "3/movie/popular?api_key=3c1275772e4f3c1e07974547aaa553e2";
    public static final String URL = "http://api.themoviedb.org/";
    public static final String KEY="3c1275772e4f3c1e07974547aaa553e2";

    public static final String PATH_KEY = "3/movie/popular?api_key="+KEY;
    public static final String PATH_KEY2 = "3/movie/top_rated?api_key="+KEY;
    public static final String TRAILER="3/movie/{id}/videos?api_key="+KEY;
    public static final String REVIEW="3/movie/{id}/reviews?api_key="+KEY;

    public static final String IMG="http://image.tmdb.org/t/p/w342";
    public static final String IMG2="http://image.tmdb.org/t/p/w780";

    public static final String dftrMovie = "daftar";

    public static final String YOUTUBE="https://www.youtube.com/watch/?v=";
    public static final String YOUTUBE_TUMBNAILs="http://img.youtube.com/vi/";

    public static final String FAVORID_PREFERENCES = "Favorite.Movies";
    public static final String FAVORID = "Favorite";
    public static Retrofit CallRetrofit(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static boolean isConnected( Context context )
    {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
