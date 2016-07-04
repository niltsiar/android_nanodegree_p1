package eu.bquepab.popularmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindInt;
import butterknife.ButterKnife;
import eu.bquepab.popularmovies.R;
import eu.bquepab.popularmovies.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieArrayAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final float POSTER_ASPECT_RATION = 1.5f;

    @BindInt(R.integer.grid_columns)
    int columnsNumber;

    private List<Movie> movies;
    private OnMovieClickListener listener;

    public MovieArrayAdapter(final List<Movie> movies, final OnMovieClickListener listener) {
        this.movies = new ArrayList<>(movies);
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        ButterKnife.bind(this, itemView);
        itemView.getLayoutParams().height = (int) (parent.getWidth() / columnsNumber * POSTER_ASPECT_RATION);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.bind(movies.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    public interface OnMovieClickListener {
        void onItemClick(Movie movie);
    }
}
