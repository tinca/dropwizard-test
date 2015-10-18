package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class Artists {
    private List<Artist> artists;

    @JsonCreator
    public Artists(@JsonProperty("artists") List<Artist> artists) {
        this.artists = artists;
    }


    public List<Artist> getArtists() {
        // TODO: copy
        return artists;
    }
}
