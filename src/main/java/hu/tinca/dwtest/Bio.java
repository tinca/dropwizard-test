package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 */
@Embeddable
public class Bio {
    @JsonProperty("published")
    @Column(name = "published", nullable = false)
    private String published;
    @JsonProperty("summary")
    @Column(name = "summary", nullable = false)
    private String summary;
    @JsonProperty("content")
    @Column(name = "content", nullable = false)
    private String content;

    @JsonCreator
    public Bio() {}

    @JsonCreator
    public Bio(@JsonProperty("published") String published,
               @JsonProperty("summary") String summary,
               @JsonProperty("content") String content) {

        this.published = published;
        this.summary = summary;
        this.content = content;
    }

    public String getPublished() {
        return published;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }


}
