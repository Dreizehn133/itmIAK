package com.example.dreizehn.mypopularmoviesproject.utility.DbConnection;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Dreizehn on 12/8/2017.
 */

public class ContentProviderMovie extends ContentProvider {
    private DbHelperMovie dbHelper;
    private final static int movie =100;
    private final static int movie_id =101;
    private final static UriMatcher uriMatch = buildUri();


    public static UriMatcher buildUri(){
        UriMatcher match = new UriMatcher(UriMatcher.NO_MATCH);
        match.addURI(ContractDbMovie.AUTHORITY,ContractDbMovie.MovieList.PATH_MOVIE,movie);
        match.addURI(ContractDbMovie.AUTHORITY,ContractDbMovie.MovieList.PATH_MOVIE+"/#",movie_id);

        return match;
    }

    @Override
    public boolean onCreate() {
        dbHelper=new DbHelperMovie(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase Db= dbHelper.getWritableDatabase();

        int mch = uriMatch.match(uri);
        Cursor cs;
        switch (mch){
            case movie:
                cs=Db.query(ContractDbMovie.MovieList.NAMA_TABEL, //select table
                        projection, //nama kolom
                        selection, //where field
                        selectionArgs, //=value
                        null,
                        null,
                        sortOrder
                        );
                break;
            default:
                throw new UnsupportedOperationException("Unknown : "+uri);

        }
        cs.setNotificationUri(getContext().getContentResolver(),uri);
        return cs;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase Db= dbHelper.getWritableDatabase();

        int mch = uriMatch.match(uri);
        Uri ur;

        switch (mch){
            case movie:
                long id =Db.insert(ContractDbMovie.MovieList.NAMA_TABEL,null,values);
                if(id>0){
                    ur= ContentUris.withAppendedId(ContractDbMovie.MovieList.CONTENT_URI,id);
                }else {
                    throw new android.database.SQLException("Failer load : "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown : "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ur;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatch.match(uri);
        // Keep track of the number of deleted tasks
        int DelFav; // starts as 0

        // COMPLETED (2) Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case movie_id:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
               DelFav = db.delete(ContractDbMovie.MovieList.NAMA_TABEL, ContractDbMovie.MovieList.KOLOM_ID+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED (3) Notify the resolver of a change and return the number of items deleted
        if (DelFav != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return DelFav ;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
