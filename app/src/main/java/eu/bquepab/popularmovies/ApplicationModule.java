package eu.bquepab.popularmovies;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

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
}
