package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "template_artists")
public class Artists {
    @Id
    private String templateArtist;
    @ElementCollection(targetClass = String.class)
    @Basic
    @CollectionTable(
            name="similar_artists",
            joinColumns=@JoinColumn(name="templateArtist")
    )
    @Column(name = "similarName")
    private List<String> artists;

    public Artists() {}

    @JsonCreator
    public Artists(@JsonProperty("templateArtist") String templateArtist, @JsonProperty("artists") List<String> artists) {
        this.templateArtist = templateArtist;
        this.artists = artists;
    }

    public String getTemplateArtist() {
        return templateArtist;
    }

    public List<String> getArtists() {
        return artists;
    }
}
