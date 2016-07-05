package eu.bquepab.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindInt;
import butterknife.BindString;
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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListActivityFragment extends Fragment implements MovieArrayAdapter.OnMovieClickListener {

    private static final String EXTRA_MOVIES = "movies";
    private static final String EXTRA_LAST_SORT = "last_sort_by";

    @Inject
    TmdbService tmdbService;

    @BindView(R.id.movies_grid)
    RecyclerView moviesRecyclerView;
    @BindInt(R.integer.grid_columns)
    int columnsNumber;
    @BindString(R.string.pref_sort_order_key)
    String prefSortOrder;
    @BindString(R.string.pref_sort_order_popularity_value)
    String prefSortOrderByPopularity;
    @BindString(R.string.pref_sort_order_top_rated_value)
    String prefSortOrderByTopRated;

    private MovieArrayAdapter movieArrayAdapter;
    private ArrayList<Movie> movies;
    private String lastSortBy;

    public MovieListActivityFragment() {
        movies = new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        PopularMoviesApplication.component().inject(this);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsNumber));
        movieArrayAdapter = new MovieArrayAdapter(movies, this);
        moviesRecyclerView.setAdapter(movieArrayAdapter);

        if (null != savedInstanceState) {
            movies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
            movieArrayAdapter.setMovies(movies);
            lastSortBy = savedInstanceState.getString(EXTRA_LAST_SORT);
        } else {
            lastSortBy = getSortByFromSettings();
            refreshMovies(lastSortBy);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final String sortByFromSettings = getSortByFromSettings();
        if (!sortByFromSettings.equals(lastSortBy)) {
            lastSortBy = sortByFromSettings;
            refreshMovies(lastSortBy);
        }
    }

    private String getSortByFromSettings() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(prefSortOrder, prefSortOrderByPopularity);
    }

    private void refreshMovies(final String sortBy) {
        Observable<DiscoverResponse> responseObservable;
        if (prefSortOrderByTopRated.equals(sortBy)) {
            responseObservable = tmdbService.getTopRatedMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        } else {
            responseObservable = tmdbService.getPopularMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        }
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discoverResponse -> {
                    movies = new ArrayList<>(discoverResponse.results());
                    movieArrayAdapter.setMovies(movies);
                });
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != movies && !movies.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_MOVIES, movies);
            outState.putString(EXTRA_LAST_SORT, lastSortBy);
        }
    }

    @Override
    public void onItemClick(final Movie movie) {
        Intent movieDetailsIntent = new Intent(getActivity(), MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE, movie);
        startActivity(movieDetailsIntent);
    }
}
