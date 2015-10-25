package hu.tinca.dwtest;

import com.codahale.metrics.annotation.Timed;
import hu.tinca.dwtest.parser.ParserException;
import hu.tinca.dwtest.parser.XmlParserUtil;
import hu.tinca.dwtest.view.ArtistView;
import hu.tinca.dwtest.view.ArtistsView;
import io.dropwizard.hibernate.UnitOfWork;

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
    private final Cache cache;

    public LastFmResource(String lastFmURI, String user, String apiKey, Cache cache) {
        this.lastFmURI = lastFmURI;
        this.user = user;
        this.apiKey = apiKey;
        this.cache = cache;
    }

    @GET
    @Timed
    @Path("/artist/{artistName}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Artists getSimilarArtists(@PathParam("artistName") String artistName) {
        try {
            // TODO: reconstruction of the whole state
//            Artists a = cache.getArtists(artistName);
//            if (a != null) {
//                return a;
//            }

            URI uri = createLastFmURI(ARTIST_SIMILAR, artistName);
            String content = readUrlContent(uri);
            Artists a = XmlParserUtil.parseArtists(content);
//            cache.put(a);
            return a;
        } catch (IOException | ParserException e) {
            throw new WebApplicationException(422);
        }
    }

    @GET
    @Timed
    @Path("/artistView/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public ArtistsView getSimilarArtistsView(@PathParam("artistName") String artistName) {
        return new ArtistsView(getSimilarArtists(artistName));
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
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Artist getArtistBio(@PathParam("artistName") String artistName) {
        try {
            Artist a = cache.getArtist(artistName);
            if (a != null) {
                return a;
            }

            URI uri = createLastFmURI(ARTIST, artistName);
            String content = readUrlContent(uri);
            a = XmlParserUtil.parseArtist(content);
            cache.put(a);
            return a;
        } catch (IOException | ParserException e) {
            LOG.log(Level.SEVERE, "", e);
            throw new WebApplicationException(422);
        }
    }

    @GET
    @Timed
    @Path("/bioView/{artistName}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public ArtistView getArtistBioView(@PathParam("artistName") String artistName) {
        return new ArtistView(getArtistBio(artistName));
    }

}
