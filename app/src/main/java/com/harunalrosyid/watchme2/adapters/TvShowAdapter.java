package com.harunalrosyid.watchme2.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harunalrosyid.watchme2.R;
import com.harunalrosyid.watchme2.entities.tvshow.TvShow;
import com.harunalrosyid.watchme2.entities.tvshow.TvShowGenre;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.harunalrosyid.watchme2.api.ApiUtils.IMAGE_URL;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {


    private final Context tvContext;
    private final PostItemListener tvItemListener;
    private List<TvShow> tvShowItems;
    private List<TvShowGenre> tvTvShowGenre;

    public TvShowAdapter(Context context, List<TvShow> posts, List<TvShowGenre> genres, PostItemListener itemListener) {
        tvTvShowGenre = genres;
        tvShowItems = posts;
        tvContext = context;
        tvItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(postView, this.tvItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TvShow tvs = tvShowItems.get(position);
        holder.tvItemReleaseDate.setText(tvs.getFirstAirDate().split("-")[0]);
        if (tvs.getOriginalName().length() > 30) {
            holder.tvItemTitle.setText((tvs.getOriginalName().substring(0, 30) + "..."));
        } else {
            holder.tvItemTitle.setText(tvs.getOriginalName());
        }
        holder.tvItemRating.setText(String.valueOf(tvs.getRating()));
        holder.tvItemGenre.setText(holder.getTvGenres(tvs.getGenreIds()));
        Glide.with(tvContext)
                .load(IMAGE_URL + tvs.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(holder.tvItemPoster);
    }

    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    public void addTvs(List<TvShow> items) {
        tvShowItems = items;
        notifyDataSetChanged();
    }

    public void addGenres(List<TvShowGenre> items) {
        tvTvShowGenre = items;
        notifyDataSetChanged();
    }

    private TvShow getItem(int adapterPosition) {
        return tvShowItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final PostItemListener tvItemListener;
        @BindView(R.id.image_poster)
        ImageView tvItemPoster;
        @BindView(R.id.release_date)
        TextView tvItemReleaseDate;
        @BindView(R.id.item_movie_title)
        TextView tvItemTitle;
        @BindView(R.id.item_movie_genre)
        TextView tvItemGenre;
        @BindView(R.id.text_rating)
        TextView tvItemRating;
        @BindView(R.id.btn_movie)
        CardView btnMovie;

        ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.tvItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        private String getTvGenres(List<Long> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Long genreId : genreIds) {
                for (TvShowGenre genre : tvTvShowGenre) {
                    if (genre.getId().equals(genreId)) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }

        @Override
        public void onClick(View view) {
            TvShow item = getItem(getAdapterPosition());
            this.tvItemListener.onPostClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
