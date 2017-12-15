package com.example.dreizehn.mypopularmoviesproject.utility.PojoPackage;

import java.util.List;

/**
 * Created by Dreizehn on 12/3/2017.
 */

public class TrailerMainPojo {
    int id;
    List<TrailerPojo> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerPojo> getResults() {
        return results;
    }

    public void setResults(List<TrailerPojo> results) {
        this.results = results;
    }
}
