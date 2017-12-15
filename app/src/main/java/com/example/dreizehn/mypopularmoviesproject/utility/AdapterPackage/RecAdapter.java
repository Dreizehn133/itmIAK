package com.example.dreizehn.mypopularmoviesproject.utility.AdapterPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dreizehn.mypopularmoviesproject.R;
import com.example.dreizehn.mypopularmoviesproject.utility.InterfacePackage.PosterKlik;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ResultPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;
import com.github.florent37.picassopalette.PicassoPalette;
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
    PosterKlik klik;
    List<ResultPojo> results;
    //kostruktor = depedency injector
//    public RecAdapter(Context con, List<ResultPojo> results) {
//        this.con = con;
//        this.results = results;
//    }

    public RecAdapter(Context con, PosterKlik klik, List<ResultPojo> results) {
        this.con = con;
        this.klik = klik;
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
        /*
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
        */
//        Picasso.with(con).load(util.IMG+res.getPosterPath())
//                .into(holder.img);
       // System.out.println("vote avg "+res.getVoteAverage());
        Picasso.with(con).load(util.IMG+res.getPosterPath())
               .into(holder.img, PicassoPalette.with(util.IMG+res.getPosterPath(),holder.img)
                       .use(PicassoPalette.Profile.MUTED_DARK)
                       .intoBackground(holder.nama).intoTextColor(holder.nama)
               .use(PicassoPalette.Profile.VIBRANT)
                .intoBackground(holder.nama, PicassoPalette.Swatch.RGB)
                .intoTextColor(holder.nama, PicassoPalette.Swatch.BODY_TEXT_COLOR)
               );
        holder.itemView.setOnClickListener(v ->{
            int itemPos = holder.getAdapterPosition();
            klik.KlikPoster(itemPos);
        });
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
            //v.setOnClickListener(this);
            ButterKnife.bind(this,v);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            ResultPojo res =this.list.get(position);
//            Intent i = new Intent(this.con, detailMovie.class);
//            i.putExtra("id",res.getId());
//            i.putExtra("nama",res.getTitle());
//            i.putExtra("back",res.getBackdropPath());
//            i.putExtra("poster",res.getPosterPath());
//            i.putExtra("date",res.getReleaseDate());
//            i.putExtra("rate",res.getVoteAverage());
//            i.putExtra("desc",res.getOverview());
//            this.con.startActivity(i);

        }
    }

}
