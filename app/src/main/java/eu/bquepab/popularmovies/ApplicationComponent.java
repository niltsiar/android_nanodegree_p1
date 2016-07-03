package eu.bquepab.popularmovies;

import dagger.Component;
import eu.bquepab.popularmovies.api.ApiModule;
import eu.bquepab.popularmovies.ui.MovieListFragment;
import eu.bquepab.popularmovies.ui.MovieViewHolder;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(MovieListFragment mainActivityFragment);

    void inject(MovieViewHolder movieViewHolder);

    final class Initializer {
        private Initializer() {
            //Avoid instances
        }

        public static ApplicationComponent init(final PopularMoviesApplication app) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .apiModule(new ApiModule())
                    .build();
        }
    }
}
