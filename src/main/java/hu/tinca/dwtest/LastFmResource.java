package hu.tinca.dwtest;

import com.codahale.metrics.annotation.Timed;
import hu.tinca.dwtest.parser.ParserException;
import hu.tinca.dwtest.parser.XmlParserUtil;
import hu.tinca.dwtest.view.ArtistView;
import hu.tinca.dwtest.view.ArtistsView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LastFmResource {
    private static final Logger LOG = Logger.getLogger(LastFmResource.class.getName());
    static final String ARTIST_SIMILAR = "artist.getSimilar";
    static final String ARTIST = "artist.getInfo";

    private final String lastFmURI;
    private final String user;
    private final String apiKey;

    public LastFmResource(String lastFmURI, String user, String apiKey) {
        this.lastFmURI = lastFmURI;
        this.user = user;
        this.apiKey = apiKey;
    }

    @GET
    @Timed
    @Path("/artist/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    public ArtistsView findSimilarArtists(@PathParam("artistName") String artistName) {
        try {
            URI uri = createLastFmURI(ARTIST_SIMILAR, artistName);
            String content = readUrlContent(uri);
            Artists a = XmlParserUtil.parseArtists(content);
            return new ArtistsView(a);
        }
        catch (IOException | ParserException e) {
            throw new WebApplicationException(422);
        }
    }

    // http://www.last.fm/api/rest
    private URI createLastFmURI(String fragment, String artist) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(artist, "UTF-8");
        return URI.create(lastFmURI).resolve("?method=" + fragment + "&artist=" + encoded + "&api_key=" + apiKey);
    }

    private String readUrlContent(URI uri) throws IOException {
        StringBuffer content = new StringBuffer();
        URLConnection conn = uri.toURL().openConnection();
        conn.setRequestProperty("User-Agent", user + " API user");  // be nice to LastFm and avoid being banned
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String l = br.readLine();
            while (l != null) {
                content.append(l);
                l = br.readLine();
            }

            return content.toString();
        }
    }

    @GET
    @Timed
    @Path("/bio/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    public ArtistView findArtistBio(@PathParam("artistName") String artistName) {
        try {
            URI uri = createLastFmURI(ARTIST, artistName);
            String content = readUrlContent(uri);
            Artist a = XmlParserUtil.parseArtist(content);
            return new ArtistView(a);
        }
        catch (IOException | ParserException e) {
            LOG.log(Level.SEVERE, "", e);
            throw new WebApplicationException(422);
        }
    }

}
