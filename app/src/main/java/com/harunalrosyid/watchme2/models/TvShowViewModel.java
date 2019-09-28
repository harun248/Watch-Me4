package com.harunalrosyid.watchme2.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harunalrosyid.watchme2.entities.tvshow.TvShow;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowGenre;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowGenresResponse;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowResponse;
import com.harunalrosyid.watchme2.api.ApiInterface;
import com.harunalrosyid.watchme2.api.ApiUtils;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.harunalrosyid.watchme2.BuildConfig.API_KEY;

public class TvShowViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<TvShow>> lisTvs = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvShowGenre>> listTvGenres = new MutableLiveData<>();
    private final MutableLiveData<TvShow> tv = new MutableLiveData<>();
    private  static final ApiInterface apiInterface = new ApiUtils().getApi();

    public LiveData<ArrayList<TvShow>> getTvs() {
        return lisTvs;
    }

    public void setTvs(final String language) {
        apiInterface.getTvs(API_KEY, language).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<TvShow> tvShows = new ArrayList<>(Objects.requireNonNull(response.body()).getTvs());
                    lisTvs.postValue(tvShows);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.i("MainActivity", "error", t);

            }
        });
    }

    public LiveData<ArrayList<TvShowGenre>> getGenres() {
        return listTvGenres;
    }

    public LiveData<TvShow> getTv() {
        return tv;
    }

    public void setTv(int tv_id, String language) {
        apiInterface.getTv(tv_id, API_KEY, language).enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tv.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void setGenre(String language) {
        apiInterface.getTvGenres(API_KEY, language).enqueue(new Callback<TvShowGenresResponse>() {
            @Override
            public void onResponse(Call<TvShowGenresResponse> call, Response<TvShowGenresResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<TvShowGenre> tvShowGenres = new ArrayList<>(Objects.requireNonNull(response.body()).getGenres());
                    listTvGenres.postValue(tvShowGenres);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<TvShowGenresResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }


}
