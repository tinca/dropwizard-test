package hu.tinca.dwtest.parser;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import hu.tinca.dwtest.Artist;
import hu.tinca.dwtest.Artists;
import hu.tinca.dwtest.Bio;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class XmlParserUtil {

    private XmlParserUtil() {
    }

    static public Artists parseArtists(String xml) throws ParserException {
        Document d = getDocument(xml);
        return getArtists(d);
    }

    static public Artist parseArtist(String xml) throws ParserException {
        Document d = getDocument(xml);
        return getArtist(d);
    }

    static private Document getDocument(String xml) throws ParserException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b;
        Document d;
        try {
            b = f.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            d = b.parse(is);
        } catch (Exception e) {
            throw new ParserException(e);
        }

        return d;
    }

    private static Artists getArtists(Document d) throws ParserException {
        List<String> artists = new ArrayList<>();
        XPath xPathEvaluator = XPathFactory.newInstance().newXPath();

        NodeList artistNameNodes;
        String artistTemplate;

        try {
            XPathExpression templateNameExpr = xPathEvaluator.compile("lfm/similarartists");
            NodeList templateNameNodes = (NodeList) templateNameExpr.evaluate(d, XPathConstants.NODESET);
            artistTemplate = templateNameNodes.item(0).getAttributes().getNamedItem("artist").getNodeValue();

            XPathExpression nameExpr = xPathEvaluator.compile("lfm/similarartists/artist/name");
            artistNameNodes = (NodeList) nameExpr.evaluate(d, XPathConstants.NODESET);
        }
        catch (XPathExpressionException e) {
            throw new ParserException(e);
        }

        for (int i = 0; i < artistNameNodes.getLength(); i++) {
            String artistName = artistNameNodes.item(i).getFirstChild().getNodeValue();
            artists.add(artistName);
        }

        return new Artists(artistTemplate, artists);
    }

    private static Artist getArtist(Document d) throws ParserException {
        XPath xPathEvaluator = XPathFactory.newInstance().newXPath();
        NodeList artistNodes;

        try {
            XPathExpression nameExpr = xPathEvaluator.compile("lfm/artist");
            artistNodes = (NodeList) nameExpr.evaluate(d, XPathConstants.NODESET);
        }
        catch (XPathExpressionException e) {
            throw new ParserException(e);
        }

        if (artistNodes.getLength() != 1) {
            return new Artist("No artist", null);
        }

        String name = getTextContent(findSubNode("name", artistNodes.item(0)));
        Node bioNode = findSubNode("bio", artistNodes.item(0));
        String pub = getTextContent(findSubNode("published", bioNode));
        String sum = getTextContent(findSubNode("summary", bioNode));
        String cont = getTextContent(findSubNode("content", bioNode));
        Bio bio = new Bio(pub, sum, cont);
        Artist artists = new Artist(name, bio);

        return artists;
    }

    private static Node findSubNode(String name, Node node) throws ParserException {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            throw new ParserException("Search node not of element type");
        }

        if (! node.hasChildNodes()) return null;

        NodeList list = node.getChildNodes();
        for (int i=0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.ELEMENT_NODE) {
                if (subnode.getNodeName().equals(name))
                    return subnode;
            }
        }

        return null;
    }

    private static String getTextContent(Node n) {
        return n == null ? "" : n.getTextContent();
    }
}
