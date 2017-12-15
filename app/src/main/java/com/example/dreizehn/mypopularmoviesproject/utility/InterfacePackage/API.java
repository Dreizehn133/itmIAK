package com.example.dreizehn.mypopularmoviesproject.utility.InterfacePackage;

import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.MainPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.ReviewPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage.TrailerMainPojo;
import com.example.dreizehn.mypopularmoviesproject.utility.util;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dreizehn on 12/1/2017.
 */

public interface API {
    @GET(util.PATH_KEY)
    Call<MainPojo> view_popular();

    @GET(util.PATH_KEY2)
    Call<MainPojo> view_topRated();

    @GET(util.TRAILER)
    Call<TrailerMainPojo> getTrailer(@Path("id") int movieId);

    @GET(util.REVIEW)
    Call<ReviewPojo> getReviews(@Path("id") int movieId);
}
