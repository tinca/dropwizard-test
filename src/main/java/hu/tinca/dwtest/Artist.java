package hu.tinca.dwtest;

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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("bio")
    public Bio getBio() {
        return bio;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("bio")
    public void setBio(Bio bio) {
        this.bio = bio;
    }
}
