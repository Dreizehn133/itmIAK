package com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dreizehn.mypopularmoviesproject.R;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.TrailerPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dreizehn on 12/3/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
    Context con;
    List<TrailerPojo> results;

    public TrailerAdapter(Context con, List<TrailerPojo> results) {
        this.con = con;
        this.results = results;
    }



    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trailer, parent, false);
        TrailerHolder holderz = new TrailerHolder(v,con,results);
        return holderz;
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        TrailerPojo res = results.get(position);
        Log.e("val", util.YOUTUBE+res.getKey());
        Picasso.with(con).load(util.YOUTUBE_TUMBNAILs+res.getKey()+"/0.jpg").into(holder.video);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context con;
        List<TrailerPojo> list = new ArrayList();

        @BindView(R.id.videoTrailer)ImageView video;

        public TrailerHolder(View itemView, Context con, List<TrailerPojo> list) {
            super(itemView);
            this.con = con;
            this.list = list;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            TrailerPojo tp = list.get(pos);

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(util.YOUTUBE+tp.getKey()));
            this.con.startActivity(i);
        }
    }
}
