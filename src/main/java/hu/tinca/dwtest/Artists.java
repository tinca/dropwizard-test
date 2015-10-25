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


    @JsonProperty("templateArtist")
    public String getTemplateArtist() {
        return templateArtist;
    }

    @JsonProperty("artists")
    public List<String> getArtists() {
        return artists;
    }

    @JsonProperty("templateArtist")
    public void setTemplateArtist(String templateArtist) {
        this.templateArtist = templateArtist;
    }

    @JsonProperty("artists")
    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
}
