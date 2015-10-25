package hu.tinca.dwtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.ResourceHelpers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ArtistsTest {
    private ObjectMapper mapper;
    String expected;


    @BeforeClass
    public void setUp() {
        mapper = Jackson.newObjectMapper();
        expected = "{\"templateArtist\":\"Cher\",\"artists\":[{\"name\":\"1\",\"bio\":{\"published\":\"published\",\"summary\":\"summary\",\"content\":\"content\"}},{\"name\":\"2\",\"bio\":null}]}";
    }

    @Test
    public void serialize() throws Exception {
        Artists a = new Artists("Cher", Arrays.asList("1", "2"));
        String actual = writeAsString(a);

        assert actual.equals(expected);
    }

    @Test
    public void deserialize() throws Exception {
        String actual = writeAsString(readArtistsFromFile());

        assert actual.equals(expected);
    }



    private String writeAsString(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    private Artists readArtistsFromFile() throws IOException {
        return mapper.readValue(getFixture(), Artists.class);
    }

    private String getFixture() {
        try {
            return Resources.toString(Resources.getResource("artists.json"), Charsets.UTF_8).trim();
        }
        catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
