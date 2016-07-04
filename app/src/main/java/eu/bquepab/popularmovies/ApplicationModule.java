package eu.bquepab.popularmovies;

import android.content.Context;
import android.os.StatFs;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class ApplicationModule {

    private final PopularMoviesApplication application;

    public ApplicationModule(final PopularMoviesApplication app) {
        this.application = app;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        final String OKHTTP_TAG = "OkHttp";
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

                @Override
                public void log(String message) {
                    Timber.tag(OKHTTP_TAG).d(message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addNetworkInterceptor(loggingInterceptor).build();
        }
        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    Picasso providesPicasso(final Context context, final OkHttpClient okHttpClient) {

        //This code for cache is taken from OkHttp3Downloader until Jake Wharton exposes it somehow
        final String PICASSO_CACHE = "picasso-cache";
        final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
        final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
        final File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }

        long size = MIN_DISK_CACHE_SIZE;
        try {
            StatFs statFs = new StatFs(cache.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            // Target 2% of the total space.
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }

        // Bound inside min/max size for disk cache.
        final long cacheSize = Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);

        final OkHttpClient cachedOkHttpClient = okHttpClient.newBuilder()
                .cache(new Cache(cache, cacheSize))
                .build();

        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(cachedOkHttpClient))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .build();
    }
}
