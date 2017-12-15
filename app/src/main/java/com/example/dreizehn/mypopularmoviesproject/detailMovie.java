package com.example.dreizehn.mypopularmoviesproject;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage.ReviewAdapter;
import com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage.TrailerAdapter;
import com.example.dreizehn.mypopularmoviesproject.utility.DbConnection.ContractDbMovie;
import com.example.dreizehn.mypopularmoviesproject.utility.InterfacePackage.API;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ReviewPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ReviewResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.TrailerMainPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.TrailerPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detailMovie extends AppCompatActivity {

    @BindView(R.id.imgBack)ImageView backdrop;
    @BindView(R.id.poster)ImageView poster;
    @BindView(R.id.movieTitle)TextView title;
    @BindView(R.id.movieRelease)TextView release;
    @BindView(R.id.movieRating)TextView rate;
    @BindView(R.id.movieDesc)TextView desc;
    @BindView(R.id.toolbar_layout)CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.rvTrailer)RecyclerView rvTrailer;
    @BindView(R.id.rvReview)RecyclerView rvReview;
    @BindView(R.id.review)TextView review;
    @BindView(R.id.trailer)TextView tlr;

    TrailerAdapter adapterVideo;
    ReviewAdapter adaterReview;
    private List<TrailerPojo> ResVideo=new ArrayList<>();
    private List<ReviewResultPojo> ResReview = new ArrayList<>();
    boolean favo=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!favo){
                    Snackbar.make(view, "DITAMBAHKAN KE FAVORITE", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    insertFromDB();
                    fab.setImageResource(R.drawable.ic_star_black_24dp);
                    favo=true;

                }else {
                    Snackbar.make(view, "DIHILANGKAN DARI FAVORITE", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    deleteFromDB();
                    fab.setImageResource(R.drawable.ic_star_border_black_24dp);
                    favo=false;
                    //Notifikasi("Favorite Dihapus","Movie dengan judul : "judul+" telah di tambahkan");
                }
            }
        });

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setDefault();
        setCollaps();
        setAdapterTrailer();
        setAdapterReview();
        if(Koneksi()){
            loadTrailer(id);
            loadReview(id);
        }else {
            Toast.makeText(this,"Mohon Periksa Internet Anda",Toast.LENGTH_LONG).show();
            tlr.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
        }
        SharedPreferences sp =getSharedPreferences(util.FAVORID_PREFERENCES,MODE_PRIVATE);
        String listFavo = sp.getString(util.FAVORID,"");
      //  Log.e("ISI SHARED PREF",listFavo);
        if(listFavo.contains(id+"")){
            fab.setImageResource(R.drawable.ic_star_black_24dp);
            favo=true;
        }

    }
    int id;
    String judul;
    String dropPath;
    String Poster;
    String date;
    float ratex;
    String oview;
    private void setDefault(){
        ResultPojo rp = getIntent().getParcelableExtra(util.dftrMovie);
        id=rp.getId();
        Poster=rp.getPosterPath();
        dropPath=rp.getBackdropPath();
        judul = rp.getTitle();
        Picasso.with(this).load(util.IMG2+dropPath ).into(backdrop);
        Picasso.with(this).load(util.IMG+ Poster).into(poster);
        title.setText(judul);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        date=rp.getReleaseDate();
        String tgl=date;
        try {
            Date d = sdf1.parse(tgl);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");
            tgl = sdf2.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ratex=rp.getVoteAverage();
        release.setText("Rilis : \n "+tgl);
        rate.setText("Rating : \n "+ratex+"/10");
        oview=rp.getOverview();
        desc.setText(oview);
    }
    private void setCollaps(){
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));
        collapsingToolbar.setTitle(judul);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadTrailer(int id){
        API api = util.CallRetrofit(util.URL).create(API.class);
        Call<TrailerMainPojo> call = api.getTrailer(id);
        call.enqueue(new Callback<TrailerMainPojo>() {

            @Override
            public void onResponse(Call<TrailerMainPojo> call, Response<TrailerMainPojo> response) {
                Log.e("LINK",response.raw().toString());
                if(response.isSuccessful()){
                    ResVideo = response.body().getResults();
                    if(ResVideo.size()<1){
                        tlr.setVisibility(View.GONE);
                    }else {
                        tlr.setVisibility(View.VISIBLE);
                        rvTrailer.setVisibility(View.VISIBLE);
                        adapterVideo = new TrailerAdapter(getApplicationContext(), ResVideo);
                        rvTrailer.setAdapter(adapterVideo);
                        adapterVideo.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerMainPojo> call, Throwable t) {
                tlr.setVisibility(View.GONE);
            }
        });

    }
    private void loadReview(int id){
        API api = util.CallRetrofit(util.URL).create(API.class);
        Call<ReviewPojo> call = api.getReviews(id);
        call.enqueue(new Callback<ReviewPojo>() {

            @Override
            public void onResponse(Call<ReviewPojo> call, Response<ReviewPojo> response) {
                Log.e("LINK",response.raw().toString());
                if(response.isSuccessful()){
                    ResReview = response.body().getResults();
                    if(ResReview.size()<1){
                        review.setVisibility(View.GONE);
                    }else {
                        review.setVisibility(View.VISIBLE);
                        rvReview.setVisibility(View.VISIBLE);
                        adaterReview = new ReviewAdapter(getApplicationContext(), ResReview);
                        rvReview.setAdapter(adaterReview);
                        adaterReview.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewPojo> call, Throwable t) {
                review.setVisibility(View.GONE);
            }
        });

    }
    private void setAdapterTrailer(){
        adapterVideo = new TrailerAdapter(this, ResVideo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,0,false);
        rvTrailer.setLayoutManager(mLayoutManager);
        rvTrailer.setItemAnimator(new DefaultItemAnimator());
        rvTrailer.setAdapter(adapterVideo);
    }
    private void setAdapterReview(){
        adaterReview = new ReviewAdapter(this,ResReview);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        rvReview.setLayoutManager(LayoutManager);
        rvReview.setItemAnimator(new DefaultItemAnimator());
        rvReview.setAdapter(adaterReview);
    }


    private boolean Koneksi(){
        return util.isConnected(this);
    }

    private void insertFromDB(){
        ContentValues cv = new ContentValues();
        cv.put(ContractDbMovie.MovieList.KOLOM_ID,id);
        cv.put(ContractDbMovie.MovieList.KOLOM_TITLE,judul);
        cv.put(ContractDbMovie.MovieList.KOLOM_RATE,ratex);
        cv.put(ContractDbMovie.MovieList.KOLOM_DATE,date);
        cv.put(ContractDbMovie.MovieList.KOLOM_OVERVIEW,oview);
        cv.put(ContractDbMovie.MovieList.KOLOM_POSTER,Poster);
        cv.put(ContractDbMovie.MovieList.KOLOM_BACKDROP,dropPath);

        Uri uri = getContentResolver().insert(ContractDbMovie.MovieList.CONTENT_URI,cv);
//        if(uri!=null){
//            Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();
//        }
    }

    private void deleteFromDB(){
        String idnya = ""+id;
        Uri ur = ContractDbMovie.MovieList.CONTENT_URI;
        ur = ur.buildUpon().appendPath(idnya).build();
        getContentResolver().delete(ur,null,null);

    }


}
