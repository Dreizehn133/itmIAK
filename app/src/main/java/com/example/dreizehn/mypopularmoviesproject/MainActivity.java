package com.example.dreizehn.mypopularmoviesproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage.RecAdapter;
import com.example.dreizehn.mypopularmoviesproject.utility.DbConnection.ContractDbMovie;
import com.example.dreizehn.mypopularmoviesproject.utility.InterfacePackage.API;
import com.example.dreizehn.mypopularmoviesproject.utility.InterfacePackage.PosterKlik;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.MainPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,PosterKlik {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @BindView(R.id.relo)
    SwipeRefreshLayout reload;

    private List<ResultPojo> results = new ArrayList<>();
    private RecAdapter Adapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // getSupportActionBar().setTitle("Popular Movies");
        tesKoneksi();

        Adapter = new RecAdapter(this, this, results);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        //RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Adapter);
        LoadData();
        reload.setOnRefreshListener(this);

        sp = getSharedPreferences(util.FAVORID_PREFERENCES,MODE_PRIVATE);
        editor=sp.edit();

    }
    int show=1;
    public void LoadData() {
        API api = util.CallRetrofit(util.URL).create(API.class);
        Call<MainPojo> call = api.view_popular();
        call.enqueue(new Callback<MainPojo>() {
            @Override
            public void onResponse(Call<MainPojo> call, Response<MainPojo> response) {
                System.out.println("resp : "+response.raw().toString());
                int page = response.body().getPage();
              //  Log.e("page",page+"");
               // Log.e("total page",response.body().getTotal_pages()+"");
               // Log.e("total view_popular",response.body().getTotal_results()+"");

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    results = response.body().getResults();
                    Adapter = new RecAdapter(getApplicationContext(),MainActivity.this::KlikPoster,results);
                    recyclerView.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"GAGAL LOAD DATA",Toast.LENGTH_SHORT).show();
            }
        });
        show=1;
        getSupportActionBar().setTitle("Popular Movie");
    }
    private void LoadDataTopRated() {
        API api = util.CallRetrofit(util.URL).create(API.class);
        Call<MainPojo> call = api.view_topRated();
        call.enqueue(new Callback<MainPojo>() {
            @Override
            public void onResponse(Call<MainPojo> call, Response<MainPojo> response) {
                System.out.println("resp : "+response.raw().toString());
                int page = response.body().getPage();
              //  Log.e("page",page+"");
               // Log.e("total page",response.body().getTotal_pages()+"");
               // Log.e("total view_popular",response.body().getTotal_results()+"");

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    results = response.body().getResults();
                    Adapter = new RecAdapter(getApplicationContext(),MainActivity.this::KlikPoster,results);
                    recyclerView.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainPojo> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"GAGAL LOAD DATA",Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
            }
        });
        show=2;
        getSupportActionBar().setTitle("Top Rated Movie");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu_movie,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_popular :
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tesKoneksi();
                LoadData();

                break;
            case R.id.action_topRated :
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tesKoneksi();
                LoadDataTopRated();

                break;
            case R.id.action_favorit :
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tesKoneksi();
                new getDbData().execute();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        reload.setRefreshing(false);
        recyclerView.setVisibility(View.GONE);
        tesKoneksi();
        if(show==1){
            LoadData();
        }else if (show==2){
            LoadDataTopRated();
        }else if(show==3){
            new getDbData().execute();
        }
    }

    @Override
    public void KlikPoster(int position) {
        ResultPojo hsl = results.get(position);
        Intent in = new Intent(this,detailMovie.class);
        in.putExtra(util.dftrMovie,hsl);
        startActivity(in);
    }

   private void tesKoneksi(){
       if(!util.isConnected(this)) {
           AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("No Internet Connection");
            ad.setMessage("Koneksi bermasalah silahkan coba lagi!").setCancelable(false)
                    .setPositiveButton("Try Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog al=ad.create();
            al.show();

           return;
       }else {

       }
   }

    @Override
    protected void onResume() {
        super.onResume();
        if(show==3){
            new getDbData().execute();
        }
    }

    private class getDbData extends AsyncTask<Void,Void,Cursor>{

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
       }


       @Override
       protected Cursor doInBackground(Void... voids) {
           Cursor cs;
           try {
               cs = getContentResolver().query(ContractDbMovie.MovieList.CONTENT_URI,
                       null,
                       null,
                       null,
                       null);
           }catch (Exception e){
               e.printStackTrace();
               return null;
           }
           return cs;
       }

       @Override
       protected void onPostExecute(Cursor cursor) {
           super.onPostExecute(cursor);
           cursor.moveToFirst();
           results.clear();
           while(!cursor.isAfterLast()){
               results.add(new ResultPojo(
                       cursor.getInt(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_ID)),
                       cursor.getFloat(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_RATE)),
                       cursor.getString(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_TITLE)),
                       cursor.getString(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_POSTER)),
                       cursor.getString(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_BACKDROP)),
                       cursor.getString(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_OVERVIEW)),
                       cursor.getString(cursor.getColumnIndex(ContractDbMovie.MovieList.KOLOM_DATE))
               ));
               cursor.moveToNext();
           }
           cursor.close();
           String dftr="";
           for (int i = 0; i < results.size(); i++) {
               ResultPojo rp = results.get(i);
              // Log.e("ID NYAAAA",rp.getId()+"");
               dftr+=","+rp.getId();
           }
           //Log.e("isi",dftr);
           editor.clear();
           editor.putString(util.FAVORID,dftr);
           editor.commit();
          // Log.e("sp",sp.getString(util.FAVORID,""));

           progressBar.setVisibility(View.GONE);
           recyclerView.setVisibility(View.VISIBLE);
           Adapter = new RecAdapter(getApplicationContext(),MainActivity.this::KlikPoster,results);
           recyclerView.setAdapter(Adapter);
           Adapter.notifyDataSetChanged();
           show=3;
           getSupportActionBar().setTitle("Favorite");
       }
   }
}
