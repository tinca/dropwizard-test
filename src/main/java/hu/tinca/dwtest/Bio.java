package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Bio {
    private String published;
    private String summary;
    private String content;

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
