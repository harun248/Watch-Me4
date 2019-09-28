package com.harunalrosyid.watchme2.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.harunalrosyid.watchme2.R;
import com.harunalrosyid.watchme2.activities.TvShowDetailActivity;
import com.harunalrosyid.watchme2.adapters.TvShowAdapter;
import com.harunalrosyid.watchme2.entities.tvshow.TvShow;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowGenre;
import com.harunalrosyid.watchme2.models.TvShowViewModel;
import com.stone.vega.library.VegaLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TvShowFragment extends Fragment {


    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    private TvShowAdapter mAdapter;
    private final Observer<ArrayList<TvShow>> getTvs = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> items) {
            if (items != null) {
                showLoading(false);
                mAdapter.addTvs(items);
            } else {
                showLoading(false);
                showError();
            }
        }
    };
    private final Observer<ArrayList<TvShowGenre>> getGenres = new Observer<ArrayList<TvShowGenre>>() {
        @Override
        public void onChanged(ArrayList<TvShowGenre> items) {
            if (items != null) {
                mAdapter.addGenres(items);
                showLoading(false);
            } else {
                showLoading(false);
                showError();
            }
        }
    };
    private TvShowViewModel tvShowViewModel;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tvshow_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkConnection();
        setupViewModeL();
        setupData();
        setupView();
    }

    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            showLoading(false);
            showError();
        }
    }

    private void setupView() {
        mAdapter = new TvShowAdapter(getContext(), new ArrayList<>(), new ArrayList<>(), id -> {
            Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
            intent.putExtra(TvShowDetailActivity.TID, id);
            startActivity(intent);
        });
        rvTv.setLayoutManager(new VegaLayoutManager());
        rvTv.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        rvTv.addItemDecoration(itemDecoration);


    }

    private void setupData() {
        String LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in_ID")) {
            LANGUAGE = "id_ID";
        }
        tvShowViewModel.setTvs(LANGUAGE);
        tvShowViewModel.setGenre(LANGUAGE);
    }

    private void setupViewModeL() {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvs().observe(this, getTvs);
        tvShowViewModel.getGenres().observe(this, getGenres);

    }

    private void showLoading(Boolean state) {
        if (state) {
            avi.show();
        } else {
            avi.hide();
        }
    }


    private void showError() {
        errorLayout.setVisibility(View.VISIBLE);
    }


}
