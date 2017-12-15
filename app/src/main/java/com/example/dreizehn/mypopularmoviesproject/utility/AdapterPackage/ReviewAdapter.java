package com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dreizehn.mypopularmoviesproject.R;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ReviewResultPojo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dreizehn on 12/4/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.RevHolder> {
    Context con;
    List<ReviewResultPojo> results;

    public ReviewAdapter(Context con, List<ReviewResultPojo> results) {
        this.con = con;
        this.results = results;
    }

    @Override
    public RevHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review, parent, false);
        RevHolder holderz = new RevHolder(v,con,results);
        return holderz;
    }

    @Override
    public void onBindViewHolder(RevHolder holder, int position) {
        ReviewResultPojo res = results.get(position);
        holder.nama.setText(res.getAuthor());
        holder.line=true;
        holder.komen.setText(res.getContent());
        holder.komen.setMaxLines(2);
        holder.more.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }



    public class RevHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context con;
        List<ReviewResultPojo> results;

        @BindView(R.id.nameBy)TextView nama;
        @BindView(R.id.reviewValue)TextView komen;
        @BindView(R.id.more)ImageView more;
        @BindView(R.id.less)ImageView less;
        public RevHolder(View itemView, Context con, List<ReviewResultPojo> results) {
            super(itemView);
            this.con = con;
            this.results = results;
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);


        }
        boolean line=true;
        @Override
        public void onClick(View v) {
            if(line){
                komen.setMaxLines(Integer.MAX_VALUE);
                line=false;
                more.setVisibility(View.GONE);
                less.setVisibility(View.VISIBLE);
            }else{
                komen.setMaxLines(2);
                line=true;
                less.setVisibility(View.GONE);
                more.setVisibility(View.VISIBLE);
            }
        }
    }
}
