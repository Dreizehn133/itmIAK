package com.example.dreizehn.mypopularmoviesproject.utility;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dreizehn on 12/1/2017.
 */

public interface API {
    @GET(util.KEY)
    Call<MainPojo> view();
}
