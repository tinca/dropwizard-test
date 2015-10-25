package hu.tinca.dwtest.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import hu.tinca.dwtest.Artist;
import hu.tinca.dwtest.Artists;
import org.testng.annotations.Test;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class XmlParserUtilTest {

    @Test
    public void parseSimilarArtists() throws ParserException, XPathExpressionException {
        String xml = getFixture("similar-artists.xml");
        Artists artists = XmlParserUtil.parseArtists(xml);

        assert artists != null;
        assert "Cher".equals(artists.getTemplateArtist());
        assert artists.getArtists().get(0).equals("Sonny and Cher");
    }

    @Test
    public void parseSimilarArtists2() throws ParserException, XPathExpressionException {
        String xml = getFixture("similar-artists2.xml");
        Artists artists = XmlParserUtil.parseArtists(xml);

        assert artists != null;
        assert "Jaco Pastorius".equals(artists.getTemplateArtist());
        assert artists.getArtists().size() == 100;
    }

    @Test
    public void parseArtist() throws ParserException, XPathExpressionException {
        String xml = getFixture("artist.xml");
        Artist artist = XmlParserUtil.parseArtist(xml);

        assert artist != null;
        assert artist.getName().equals("Cher");
    }


    private String getFixture(String resourceName) {
        try {
            return Resources.toString(Resources.getResource(resourceName), Charsets.UTF_8).trim();
        }
        catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
