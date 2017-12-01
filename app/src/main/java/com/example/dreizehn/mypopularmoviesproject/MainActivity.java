package com.example.dreizehn.mypopularmoviesproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dreizehn.mypopularmoviesproject.utility.API;
import com.example.dreizehn.mypopularmoviesproject.utility.MainPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.ResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
ProgressBar progressBar;
    private List<ResultPojo> results = new ArrayList<>();
    private RecAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Adapter = new RecAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        //RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Adapter);
        LoadData();
    }
    private void LoadData() {
       // Adapter.notifyDataSetChanged();
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(util.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<MainPojo> call = api.view();
        call.enqueue(new Callback<MainPojo>() {
            @Override
            public void onResponse(Call<MainPojo> call, Response<MainPojo> response) {
                System.out.println("resp : "+response.raw().toString());
                int page = response.body().getPage();
                Log.e("page",page+"");
                Log.e("total page",response.body().getTotal_pages()+"");
                Log.e("total view",response.body().getTotal_results()+"");

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    results = response.body().getResults();
                    Adapter = new RecAdapter(getApplicationContext(), results);
                    recyclerView.setAdapter(Adapter);
                }
            }

            @Override
            public void onFailure(Call<MainPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"GAGAL LOAD DATA",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
