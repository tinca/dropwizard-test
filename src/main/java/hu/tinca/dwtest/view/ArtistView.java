package hu.tinca.dwtest.view;


import hu.tinca.dwtest.Artist;
import io.dropwizard.views.View;

/**
 *
 */
public class ArtistView extends View {
    private final Artist artist;

    public ArtistView(Artist artist) {
        super("artist.ftl");
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }
}
