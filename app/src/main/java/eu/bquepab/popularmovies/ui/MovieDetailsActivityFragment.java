package eu.bquepab.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import eu.bquepab.popularmovies.PopularMoviesApplication;
import eu.bquepab.popularmovies.R;
import eu.bquepab.popularmovies.model.Movie;
import java.util.Locale;
import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.movie_user_rating)
    TextView movieUserRating;
    @BindView(R.id.movie_synopsis)
    TextView movieSynopsis;
    @Inject
    Picasso picasso;
    private Movie movie;

    public MovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        PopularMoviesApplication.component().inject(this);
        ButterKnife.bind(this, view);
        movie = getActivity().getIntent().getParcelableExtra(MovieDetailsActivity.MOVIE);

        picasso.load(movie.posterUrl()).into(moviePoster);
        movieTitle.setText(movie.title());
        movieReleaseDate.setText(movie.releaseDate());
        movieUserRating.setText(String.format(Locale.getDefault(), "%.2f", movie.userRating()));
        movieSynopsis.setText(movie.synopsis());

        return view;
    }
}
