package com.harunalrosyid.watchme2.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harunalrosyid.watchme2.R;
import com.harunalrosyid.watchme2.entities.favorite.Favorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.harunalrosyid.watchme2.api.ApiUtils.IMAGE_URL;

public class TvShowFavAdapter extends RecyclerView.Adapter<TvShowFavAdapter.Viewholder> {
    private final ArrayList<Favorite> favorites = new ArrayList<>();
    private final Activity activity;
    private final PostItemListener tvPostItemListener;

    public TvShowFavAdapter(Activity activity, PostItemListener postItemListener) {
        this.activity = activity;
        this.tvPostItemListener = postItemListener;
    }

    public ArrayList<Favorite> getListFavorite() {
        return favorites;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite) {

        if (listFavorite.size() > 0) {
            this.favorites.clear();
        }
        this.favorites.addAll(listFavorite);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.favorite_item, parent, false);

        return new Viewholder(postView, this.tvPostItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Favorite favorite = favorites.get(position);
        if (favorite.getTitle().length() > 15) {
            holder.tvItemTitle.setText((favorite.getTitle().substring(0, 15) + "..."));
        } else {
            holder.tvItemTitle.setText(favorite.getTitle());
        }
        holder.tvItemRating.setText(favorite.getRating());
        holder.tvItemReleaseDate.setText(favorite.getReleaseDate().split("-")[0]);
        Glide.with(activity)
                .load(IMAGE_URL + favorites.get(position).getPoster())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(holder.tvItemPoster);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    private Favorite getItem(int adapterPosition) {
        return favorites.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int mId);
    }

    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final PostItemListener tvItemListener;
        @BindView(R.id.image_poster)
        ImageView tvItemPoster;
        @BindView(R.id.release_date)
        TextView tvItemReleaseDate;
        @BindView(R.id.item_movie_title)
        TextView tvItemTitle;
        @BindView(R.id.text_rating)
        TextView tvItemRating;
        @BindView(R.id.btn_movie)
        CardView btnMovie;

        Viewholder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.tvItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Favorite item = getItem(getAdapterPosition());
            this.tvItemListener.onPostClick(item.getmId());
            notifyDataSetChanged();
        }
    }
}
