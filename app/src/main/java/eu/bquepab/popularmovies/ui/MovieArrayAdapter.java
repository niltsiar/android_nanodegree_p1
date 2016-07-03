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

    private static float POSTER_ASPECT_RATION = 1.5f;

    @BindInt(R.integer.grid_columns)
    int columnsNumber;

    private List<Movie> movies;

    public MovieArrayAdapter(final List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        ButterKnife.bind(this, itemView);
        itemView.getLayoutParams().height = (int) (parent.getWidth() / columnsNumber * POSTER_ASPECT_RATION);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.setMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }
}
