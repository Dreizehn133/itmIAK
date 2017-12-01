package com.example.dreizehn.mypopularmoviesproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dreizehn.mypopularmoviesproject.utility.ResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dreizehn on 12/1/2017.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    Context con;
    List<ResultPojo> results;
    //kostruktor = depedency injector
    public RecAdapter(Context con, List<ResultPojo> results) {
        this.con = con;
        this.results = results;
    }

    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder holderz = new ViewHolder(v,con,results);
        return holderz;
    }

    @Override
    public void onBindViewHolder(RecAdapter.ViewHolder holder, int position) {
        ResultPojo res = results.get(position);
        holder.nama.setText(res.getTitle());
        System.out.println("========================================================");
        System.out.println("poster  "+res.getPosterPath()); //null
        System.out.println("original title  "+res.getOriginalTitle()); //null
        System.out.println("overview  "+res.getOverview());
        System.out.println("backdrop "+res.getBackdropPath()); //null
        System.out.println("language "+res.getOriginalLanguage()); //null
        System.out.println("release date "+res.getReleaseDate()); //null
        System.out.println("genre ids "+res.getGenreIds()); //null
        System.out.println("vote avg "+res.getVoteAverage()); //0.0
        System.out.println("popular "+res.getPopularity());
        System.out.println("vode count "+res.getVoteCount()); //null
        System.out.println("video "+res.getVideo());
        System.out.println("adult "+res.getAdult());
        System.out.println("id "+res.getId());
        System.out.println("========================================================");

        Picasso.with(con).load(util.IMG+"/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg")
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.nama)
        TextView nama;

        Context con;
        List<ResultPojo> list = new ArrayList();
        public ViewHolder(View v, Context con, List<ResultPojo> results) {
            super(v);
            this.con=con;
            this.list=results;
            ButterKnife.bind(this,v);
        }

        @Override
        public void onClick(View v) {
            Log.e("eror","SABARRRRR");
        }
    }

}
