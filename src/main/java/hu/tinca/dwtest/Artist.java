package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "artist")
public class Artist {
    @Id
    private String name;
    @Embedded
    private Bio bio;

    public Artist() {}

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
