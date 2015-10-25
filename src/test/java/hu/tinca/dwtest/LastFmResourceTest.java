package hu.tinca.dwtest;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockito.Mockito.*;


/**
 *  To test our resource <a href="http://www.mock-server.com">MockServer</a> is used for mocking LastFm
 *  and a mocked cache.
 */
public class LastFmResourceTest {
    private static ClientAndServer mockServer;
    private static Cache mockCache = mock(Cache.class);

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new LastFmResource("http://localhost:8888", "", "", mockCache))
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
        String response = getSimilarArtistsResponse();
        setUpMockServer(response, LastFmResource.ARTIST_SIMILAR);

        Artists a = callOurRestApi("/artist/", Artists.class);

        Assert.assertEquals("Sonny and Cher", a.getArtists().get(0));
    }

    @Test
    public void artist() {
        String response = getArtistResponse();
        setUpMockServer(response, LastFmResource.ARTIST);

        Artist a = callOurRestApi("/bio/", Artist.class);

        Assert.assertEquals("Cher", a.getName());
        Bio bio = a.getBio();
        Assert.assertEquals("Thu, 13 Mar 2008 03:59:18 +0000", bio.getPublished());
        Assert.assertEquals("this is a summary", bio.getSummary());
        Assert.assertEquals("this is a content", bio.getContent());
    }



    private void setUpMockServer(String response, String uri) {
        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(uri + "any"), // LastFm API uri fragment
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody(response)
                );
    }

    private <T> T callOurRestApi(String target, Class<T> clz)  {
        return resource.client().target(target + "any").request().get(clz);
    }

    private String getSimilarArtistsResponse() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<lfm status=\"ok\">\n" +
                "    <similarartists artist=\"Cher\">\n" +
                "        <artist>\n" +
                "            <name>Sonny and Cher</name>\n" +
                "            <mbid>3d6e4b6d-2700-458c-9722-9021965a8164</mbid>\n" +
                "            <match>1</match>\n" +
                "            <url>www.last.fm/music/Sonny%2B%2526%2BCher</url>\n" +
                "            <streamable>1</streamable>\n" +
                "        </artist>\n" +
                "    </similarartists>\n" +
                "</lfm>";
    }

    private String getArtistResponse() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<lfm status=\"ok\">\n" +
                "    <artist>\n" +
                "        <name>Cher</name>\n" +
                "        <mbid>bfcc6d75-a6a5-4bc6-8282-47aec8531818</mbid>\n" +
                "        <url>http://www.last.fm/music/Cher</url>\n" +
                "        <bio>\n" +
                "            <published>Thu, 13 Mar 2008 03:59:18 +0000</published>\n" +
                "            <summary>this is a summary</summary>\n" +
                "            <content>this is a content</content>\n" +
                "        </bio>\n" +
                "    </artist>\n" +
                "</lfm>";
    }
}
