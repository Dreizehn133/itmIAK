package com.example.dreizehn.mypopularmoviesproject.utility;

import java.util.List;

/**
 * Created by Dreizehn on 12/1/2017.
 */

public class MainPojo {
    int page,total_results,total_pages;
    List<ResultPojo> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultPojo> getResults() {
        return results;
    }

    public void setResults(List<ResultPojo> results) {
        this.results = results;
    }
}
