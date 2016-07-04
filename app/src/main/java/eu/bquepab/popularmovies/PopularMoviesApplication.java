package eu.bquepab.popularmovies;

import android.app.Application;
import timber.log.Timber;

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent component() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = DaggerApplicationComponent.Initializer.init(this);
    }
}
