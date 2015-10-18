package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Artist {
    private String name;
    private Bio bio;

    public Artist(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonCreator
    public Artist(@JsonProperty("name") String name, @JsonProperty("bio") Bio bio) {
        this.name = name;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public Bio getBio() {
        return bio;
    }
}
