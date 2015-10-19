package hu.tinca.dwtest.view;

import hu.tinca.dwtest.Artist;
import hu.tinca.dwtest.Artists;
import io.dropwizard.views.View;

import java.util.List;

/**
 *
 */
public class ArtistsView extends View {
    private final Artists artists;

    public ArtistsView(Artists artists) {
        super("artists.ftl");
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists.getArtists();
    }
}
