package eu.bquepab.popularmovies.api;

import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import eu.bquepab.popularmovies.BuildConfig;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Retrofit providesRetrofit(final OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.THE_MOVE_DATABASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(providesMoshiConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    TmdbService providesTmdbService(final Retrofit retrofit) {
        return retrofit.create(TmdbService.class);
    }

    private Converter.Factory providesMoshiConverterFactory() {
        return MoshiConverterFactory.create(new Moshi.Builder()
                                                    .add(new AutoValueMoshiAdapterFactory())
                                                    .build());
    }
}
