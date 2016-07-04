package eu.bquepab.popularmovies.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TmdbService {

    String SORT_BY_POPULARITY = "popularity.desc";
    String SORT_BY_TOP_RATED = "vote_average.desc";

    @GET("discover/movie")
    Observable<DiscoverResponse> discoverMovies(@Query("sort_by") String sort, @Query("api_key") String apiKey);
}
