package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 */
public class LastFmConfiguration extends Configuration {
    @NotEmpty
    private String lastFmURI;
    @NotEmpty
    private String user;
    @NotEmpty
    private String apiKey;

    public String getLastFmURI() {
        return lastFmURI;
    }

    @JsonProperty
    public void setLastFmURI(String lastFmURI) {
        this.lastFmURI = lastFmURI;
    }

    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty
    public String getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(String user) {
        this.user = user;
    }
}
