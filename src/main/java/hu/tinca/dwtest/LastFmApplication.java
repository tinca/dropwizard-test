package hu.tinca.dwtest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 *
 */
public class LastFmApplication extends Application<LastFmConfiguration> {

    public static void main(String[] args) throws Exception {
        new LastFmApplication().run(args);
    }

    public void initialize(Bootstrap<LastFmConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<LastFmConfiguration>());
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
                cfg.getApiKey()
        );

    }

    private void registerLastFmAvailabilityCheck(Environment environment, String lastFmURI) {
        environment.healthChecks().register("lfmAvailabilty", new LastFmAvailabilityCheck(lastFmURI));
    }

}
