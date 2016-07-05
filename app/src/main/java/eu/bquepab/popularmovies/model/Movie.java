package eu.bquepab.popularmovies.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import eu.bquepab.popularmovies.BuildConfig;

@AutoValue
public abstract class Movie implements Parcelable {

    public static JsonAdapter<Movie> jsonAdapter(final Moshi moshi) {
        return new AutoValue_Movie.MoshiJsonAdapter(moshi);
    }

    public abstract int id();

    public abstract String title();

    @Json(name = "poster_path")
    @Nullable
    public abstract String posterPath();

    @Json(name = "overview")
    public abstract String synopsis();

    @Json(name = "vote_average")
    public abstract float userRating();

    @Json(name = "release_date")
    public abstract String releaseDate();

    public String posterUrl() {
        if (!TextUtils.isEmpty(posterPath())) {
            return BuildConfig.THE_MOVIE_DATABASE_IMAGE_URL + posterPath();
        } else {
            return BuildConfig.THE_MOVIE_DATABASE_NO_POSTER_IMAGE_URL;
        }
    }
}
