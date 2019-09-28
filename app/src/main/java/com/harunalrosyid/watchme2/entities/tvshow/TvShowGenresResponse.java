package com.harunalrosyid.watchme2.entities.tvshow;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TvShowGenresResponse {

    @SerializedName("genres")
    private List<TvShowGenre> mGenres;

    public List<TvShowGenre> getGenres() {
        return mGenres;
    }

}
