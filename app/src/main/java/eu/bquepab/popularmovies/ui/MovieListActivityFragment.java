package eu.bquepab.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.bquepab.popularmovies.BuildConfig;
import eu.bquepab.popularmovies.PopularMoviesApplication;
import eu.bquepab.popularmovies.R;
import eu.bquepab.popularmovies.api.DiscoverResponse;
import eu.bquepab.popularmovies.api.TmdbService;
import eu.bquepab.popularmovies.model.Movie;
import java.util.ArrayList;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListActivityFragment extends Fragment implements MovieArrayAdapter.OnMovieClickListener {

    @Inject
    TmdbService tmdbService;
    @BindView(R.id.movies_grid)
    RecyclerView moviesRecyclerView;
    @BindInt(R.integer.grid_columns)
    int columnsNumber;
    private MovieArrayAdapter movieArrayAdapter;

    public MovieListActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        PopularMoviesApplication.component().inject(this);
        ButterKnife.bind(this, view);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsNumber));
        movieArrayAdapter = new MovieArrayAdapter(new ArrayList<Movie>(), this);
        moviesRecyclerView.setAdapter(movieArrayAdapter);

        tmdbService.discoverMovies("popularity.desc", BuildConfig.THE_MOVIE_DATABASE_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DiscoverResponse>() {
                    @Override
                    public void call(DiscoverResponse discoverResponse) {
                        movieArrayAdapter.setMovies(discoverResponse.results());
                    }
                });

        return view;
    }

    @Override
    public void onItemClick(final Movie movie) {
        Intent movieDetailsIntent = new Intent(getActivity(), MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE, movie);
        startActivity(movieDetailsIntent);
    }
}
