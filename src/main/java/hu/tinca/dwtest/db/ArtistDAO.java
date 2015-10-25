package hu.tinca.dwtest.db;

import com.google.common.base.Optional;
import hu.tinca.dwtest.Artist;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 *
 */
public class ArtistDAO extends AbstractDAO<Artist> {

    public ArtistDAO(SessionFactory sf) {
        super(sf);
    }

    public Artist findById(String name) {
        return get(name);
    }

    public void create(Artist artist) {
        persist(artist);
    }

}
