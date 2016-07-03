package eu.bquepab.popularmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    public MovieViewHolder(final View itemView) {
        super(itemView);
        PopularMoviesApplication.component().inject(this);
        ButterKnife.bind(this, itemView);
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
        picasso.load(movie.posterUrl()).into(moviePoster);
    }
}
