package hu.tinca.dwtest.db;

import com.google.common.base.Optional;
import hu.tinca.dwtest.Artists;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 *
 */
public class ArtistsDAO extends AbstractDAO<Artists> {

    public ArtistsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Artists findById(String name) {
        return get(name);
    }

    public Artists create(Artists artists) {
        return persist(artists);
    }

}
