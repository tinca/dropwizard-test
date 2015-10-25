package hu.tinca.dwtest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
