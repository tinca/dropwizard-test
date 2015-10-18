package hu.tinca.dwtest;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.*;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Cookie;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;

import javax.validation.constraints.AssertTrue;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;


/**
 *  To test our resource <a href="http://www.mock-server.com">MockServer</a> is used for mocking LastFm.
 */
public class LastFmResourceTest {
    private static ClientAndServer mockServer;
    private static String TEST_ARTIST = "HeyJoe";

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new LastFmResource("http://localhost:8888", "", ""))
            .build();


    @BeforeClass
    public static void setUp() {
        mockServer = startClientAndServer(8888);
    }

    @AfterClass
    public static void tearDown() {
        mockServer.stop();
    }

    @Test
    public void similarArtists() {
        String response = "heyyy";
        setUpMockServer(response, LastFmResource.ARTIST_SIMILAR);

        Artists a = callOurRestApi("/artist/", Artists.class);

        Assert.assertEquals(response, a.getArtists().get(0).getName());
    }

    @Test
    public void artist() {
        String response = "thisisbio";
        setUpMockServer(response, LastFmResource.ARTIST);

        Artist a = callOurRestApi("/bio/", Artist.class);

        Assert.assertEquals(response, a.getName());
    }



    private void setUpMockServer(String response, String uri) {
        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(uri + TEST_ARTIST), // LastFm API uri fragment
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400")
                                )
                                .withBody(response)
                );
    }

    private <T> T callOurRestApi(String target, Class<T> clz)  {
        return resource.client().target(target + TEST_ARTIST).request().get(clz);
    }
}
