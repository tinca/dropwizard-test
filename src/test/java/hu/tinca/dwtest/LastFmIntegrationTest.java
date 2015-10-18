package hu.tinca.dwtest;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

/**
 *
 */
public class LastFmIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<LastFmConfiguration> RULE =
            new DropwizardAppRule<>(LastFmApplication.class, ResourceHelpers.resourceFilePath("test-app-config.yaml"));

    @Test
    public void querySimilarArtists() {
        Client client = JerseyClientBuilder.createClient();

        Response response = client.target("http://localhost:8080/artist/Pastorius")
                .request().get();

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void queryArtist() {
        Client client = JerseyClientBuilder.createClient();

        Response response = client.target("http://localhost:8080/bio/Jaco Pastorius")
                .request().get();

        Assert.assertEquals(200, response.getStatus());
    }
}
