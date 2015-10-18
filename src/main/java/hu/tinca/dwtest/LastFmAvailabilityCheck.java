package hu.tinca.dwtest;

import com.codahale.metrics.health.HealthCheck;

import java.net.HttpURLConnection;
import java.net.URI;

/**
 *
 */
public class LastFmAvailabilityCheck extends HealthCheck {
    private String uri;

    public LastFmAvailabilityCheck(String uri) {
        this.uri = uri;
    }

    @Override
    protected Result check() throws Exception {
        HttpURLConnection c = (HttpURLConnection) URI.create(uri).toURL().openConnection();
        try {
            c.connect();
            return c.getResponseCode() == HttpURLConnection.HTTP_OK ? Result.healthy() : Result.unhealthy("LastFm not available");
        }
        finally {
            c.disconnect();
        }
    }
}
