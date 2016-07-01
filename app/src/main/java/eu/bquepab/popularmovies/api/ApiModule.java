package eu.bquepab.popularmovies.api;

import android.content.Context;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import eu.bquepab.popularmovies.BuildConfig;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

                @Override
                public void log(String message) {
                    Timber.tag("OkHttp").d(message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor).build();
        }
        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(final OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.THE_MOVE_DATABASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(provideMoshiConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    TmdbService providesTmdbService(final Retrofit retrofit) {
        return retrofit.create(TmdbService.class);
    }

    @Provides
    @Singleton
    Picasso providePicasso(final Context context, final OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    private final Converter.Factory provideMoshiConverterFactory() {
        return MoshiConverterFactory.create(new Moshi.Builder()
                                                    .add(new AutoValueMoshiAdapterFactory())
                                                    .build());
    }
}
