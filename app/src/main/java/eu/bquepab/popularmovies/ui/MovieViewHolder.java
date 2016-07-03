package eu.bquepab.popularmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import eu.bquepab.popularmovies.PopularMoviesApplication;
import eu.bquepab.popularmovies.R;
import eu.bquepab.popularmovies.model.Movie;
import javax.inject.Inject;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @Inject
    Picasso picasso;

    private Movie movie;
    private MovieArrayAdapter.OnMovieClickListener listener;

    public MovieViewHolder(final View itemView) {
        super(itemView);
        PopularMoviesApplication.component().inject(this);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Movie movie, final MovieArrayAdapter.OnMovieClickListener listener) {
        this.movie = movie;
        this.listener = listener;
        picasso.load(movie.posterUrl()).into(moviePoster);
    }

    @OnClick(R.id.movie_poster)
    void onClick() {
        listener.onItemClick(movie);
    }
}
