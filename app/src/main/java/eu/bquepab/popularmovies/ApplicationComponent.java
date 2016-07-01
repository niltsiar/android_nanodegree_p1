package eu.bquepab.popularmovies;

import dagger.Component;
import eu.bquepab.popularmovies.api.ApiModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApiModule.class})
public interface ApplicationComponent {

    void inject(MainActivityFragment mainActivityFragment);

    final class Initializer {
        private Initializer() {
            //Avoid instances
        }

        public static ApplicationComponent init(final PopularMoviesApplication app) {
            return DaggerApplicationComponent.builder()
                    .apiModule(new ApiModule())
                    .build();
        }
    }

}
