package com.harunalrosyid.watchme2.api;

import com.harunalrosyid.watchme2.entities.movie.Movie;
import com.harunalrosyid.watchme2.entities.movie.MovieGenresResponse;
import com.harunalrosyid.watchme2.entities.movie.MoviesResponse;
import com.harunalrosyid.watchme2.entities.tvshow.TvShow;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowGenresResponse;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MoviesResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TvShowResponse> getTvs(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );


    @GET("genre/movie/list")
    Call<MovieGenresResponse> getMovieGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/tv/list")
    Call<TvShowGenresResponse> getTvGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<TvShow> getTv(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}