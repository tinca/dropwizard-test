package hu.tinca.dwtest.db;

import hu.tinca.dwtest.Artist;
import hu.tinca.dwtest.Artists;
import hu.tinca.dwtest.Cache;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstration of RDBMS persistence.
 */
public class CacheImpl implements Cache {
    private static final Logger LOG = Logger.getLogger(CacheImpl.class.getName());
    private ArtistDAO artistDAO;
    private ArtistsDAO artistsDAO;

    public CacheImpl(ArtistDAO artistDAO, ArtistsDAO artistsDAO) {
        this.artistDAO = artistDAO;
        this.artistsDAO = artistsDAO;
    }

    @Override
    public void put(Artist artist) {
        try {
            artistDAO.create(artist);
        } catch (Throwable t) {
            LOG.log(Level.WARNING, "", t);
        }
    }


    @Override
    public void put(Artists artists) {
        try {
            artistsDAO.create(artists);
        } catch (Throwable t) {
            LOG.log(Level.WARNING, "", t);
        }
    }

    @Override
    public Artist getArtist(String name) {
        try {
            return artistDAO.findById(name);
        } catch (Throwable t) {
            LOG.log(Level.WARNING, "", t);
        }

        return null;
    }

    @Override
    public Artists getArtists(String name) {
        try {
            return artistsDAO.findById(name);
        } catch (Throwable t) {
            LOG.log(Level.WARNING, "", t);
        }

        return null;
    }
}
