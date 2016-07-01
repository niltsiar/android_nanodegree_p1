package eu.bquepab.popularmovies;

import android.app.Application;

public class PopularMoviesApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent component() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = DaggerApplicationComponent.Initializer.init(this);
    }
}
