package hu.tinca.dwtest;

import hu.tinca.dwtest.db.ArtistDAO;
import hu.tinca.dwtest.db.ArtistsDAO;
import hu.tinca.dwtest.db.CacheImpl;
import io.dropwizard.Application;
import io.dropwizard.Bundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 *
 */
public class LastFmApplication extends Application<LastFmConfiguration> {
    private HibernateBundle<LastFmConfiguration> hibernateBundle;

    public static void main(String[] args) throws Exception {
        new LastFmApplication().run(args);
    }

    public void initialize(Bootstrap<LastFmConfiguration> bootstrap) {
        bootstrap.addBundle(createMigrations());
        hibernateBundle = createHibernateBundle();
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<LastFmConfiguration>());
    }

    private Bundle createMigrations() {
        return new MigrationsBundle<LastFmConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LastFmConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    private HibernateBundle<LastFmConfiguration> createHibernateBundle() {
        return new HibernateBundle<LastFmConfiguration>(Artist.class, Artists.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(LastFmConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Override
    public void run(LastFmConfiguration cfg, Environment env) throws Exception {
        registerLastFmAvailabilityCheck(env, cfg.getLastFmURI());
        LastFmResource r = createResource(cfg);
        env.jersey().register(r);
    }

    private LastFmResource createResource(LastFmConfiguration cfg) {
        return new LastFmResource(
                cfg.getLastFmURI(),
                cfg.getUser(),
                cfg.getApiKey(),
                createCache()
        );
    }

    private Cache createCache() {
//        Session session = hibernateBundle.getSessionFactory().openSession();
//        session.setDefaultReadOnly(true);
//        session.setCacheMode(CacheMode.NORMAL);
//        session.setFlushMode(FlushMode.MANUAL);
//        ManagedSessionContext.bind(session);
        ArtistDAO artistDAO = new ArtistDAO(hibernateBundle.getSessionFactory());
        ArtistsDAO artistsDAO = new ArtistsDAO(hibernateBundle.getSessionFactory());
        return new CacheImpl(artistDAO, artistsDAO);
    }

    private void registerLastFmAvailabilityCheck(Environment environment, String lastFmURI) {
        environment.healthChecks().register("lfmAvailabilty", new LastFmAvailabilityCheck(lastFmURI));
    }

}
