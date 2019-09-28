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
import com.harunalrosyid.watchme2.entities.movie.Movie;
import com.harunalrosyid.watchme2.entities.movie.MovieGenre;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.harunalrosyid.watchme2.api.ApiUtils.IMAGE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context mvContext;
    private final PostItemListener mvItemListener;
    private List<Movie> mvItems;
    private List<MovieGenre> movieGenre;

    public MovieAdapter(Context context, List<Movie> posts, List<MovieGenre> genres, PostItemListener itemListener) {
        movieGenre = genres;
        mvItems = posts;
        mvContext = context;
        mvItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(postView, this.mvItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = mvItems.get(position);
        holder.mvItemReleaseDate.setText(movie.getReleaseDate().split("-")[0]);
        if (movie.getTitle().length() > 30) {
            holder.mvItemTitle.setText((movie.getTitle().substring(0, 30) + "..."));
        } else {
            holder.mvItemTitle.setText(movie.getTitle());
        }
        holder.mvItemRating.setText(String.valueOf(movie.getRating()));
        holder.mvItemGenre.setText(holder.getMovieGenres(movie.getGenreIds()));
        Glide.with(mvContext)
                .load(IMAGE_URL + movie.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(holder.mvItemPoster);
    }

    @Override
    public int getItemCount() {
        return mvItems.size();
    }

    public void addMovies(List<Movie> items) {
        mvItems = items;
        notifyDataSetChanged();
    }

    public void addGenres(List<MovieGenre> items) {
        movieGenre = items;
        notifyDataSetChanged();
    }

    private Movie getItem(int adapterPosition) {
        return mvItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final PostItemListener mvItemListener;
        @BindView(R.id.image_poster)
        ImageView mvItemPoster;
        @BindView(R.id.release_date)
        TextView mvItemReleaseDate;
        @BindView(R.id.item_movie_title)
        TextView mvItemTitle;
        @BindView(R.id.item_movie_genre)
        TextView mvItemGenre;
        @BindView(R.id.text_rating)
        TextView mvItemRating;
        @BindView(R.id.btn_movie)
        CardView btnMovie;

        ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mvItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        private String getMovieGenres(List<Long> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Long genreId : genreIds) {
                for (MovieGenre genre : movieGenre) {
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
            Movie item = getItem(getAdapterPosition());
            this.mvItemListener.onPostClick(item.getId());
            notifyDataSetChanged();
        }
    }
}